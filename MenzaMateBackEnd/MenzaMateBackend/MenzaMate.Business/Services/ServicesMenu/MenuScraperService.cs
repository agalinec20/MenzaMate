using AutoMapper;
using HtmlAgilityPack;
using MenzaMate.Business.Models.ModelsMenu;
using MenzaMate.Business.Services.INameService;
using MenzaMate.Data.Generic;
using MenzaMateBackend.Data.Entities;
using System.Text.RegularExpressions;

namespace MenzaMate.Business.Services.ServicesMenu
{
    public class MenuScraperService : IMenuScraperService
    {
        private readonly HttpClient _httpClient;
        private readonly IMenuService _menuService;
        private readonly IMapper _mapper;
        private readonly IRepository<Menu> _menuRepository;

        public MenuScraperService(IMenuService menuService, IMapper mapper, HttpClient httpClient, IRepository<Menu> menuRepository)
        {
            _menuService = menuService;
            _mapper = mapper;
            _httpClient = httpClient;
            _menuRepository = menuRepository;
        }

        public async Task ScrapeMenusAndSave()
        {
            string url = "https://www.scvz.unizg.hr/jelovnik-varazdin/";
            var menus = await ScrapeMenus(url);

            var menuEntities = _mapper.Map<List<Menu>>(menus);
            foreach (var menu in menuEntities)
            {
                _menuRepository.Add(menu);
            }
            await _menuRepository.SaveAsync();
        }

        public async Task<List<MenuScraperDto>> ScrapeMenus(string url)
        {
            var response = await _httpClient.GetStringAsync(url);
            var htmlDoc = new HtmlDocument();
            htmlDoc.LoadHtml(response);

            var menus = new List<MenuScraperDto>();

            // Pronađi aktivni jelovnik
            var menusNode = htmlDoc.DocumentNode.SelectSingleNode("//div[contains(@class, 'jelovnik-content active')]");

            if (menusNode == null)
            {
                throw new Exception("Failed to scrape active menu data.");
            }

            var menuDate = DateTime.Now;

            var dateContentNodes = menusNode.SelectNodes(".//div[contains(@class, 'jelovnik-date-content')]");

            if (dateContentNodes == null)
            {
                throw new Exception("No date content found.");
            }

            foreach (var dateContentNode in dateContentNodes)
            {
                var mealTypeNode = dateContentNode.SelectSingleNode(".//h3");
                var mealType = mealTypeNode?.InnerText?.Trim();

                if (string.IsNullOrEmpty(mealType)) continue;

                var mealItems = dateContentNode.SelectNodes(".//div[contains(@class, 'col-md-4')]");

                if (mealItems == null) continue;

                int index = 1;
                foreach (var item in mealItems)
                {
                    var description = item.SelectSingleNode(".//p")?.InnerText?.Trim();

                    if (string.IsNullOrEmpty(description)) continue;

                    description = Regex.Replace(description, @"^\d+\.\s*", "").Replace("\r\n", ", ");

                    menus.Add(new MenuScraperDto
                    {
                        Title = $"{mealType} {index}",
                        Description = description,
                        CreatedDate = menuDate.Date
                    });

                    index++;
                }
            }

            return menus;
        }
    }
}
