using AutoMapper;
using MenzaMate.Business.Helpers;
using MenzaMate.Business.Models.ModelsMenu;
using MenzaMate.Business.Services.INameService;
using MenzaMate.Data.Generic;
using MenzaMateBackend.Data.Entities;
using Microsoft.EntityFrameworkCore;

namespace MenzaMate.Business.Services
{
    namespace MenzaMate.Business.Services
    {
        public class MenuService : IMenuService
        {
            private readonly IRepository<Menu> _menuRepository;
            private readonly IMapper _mapper;

            public MenuService(IRepository<Menu> menuRepository, IMapper mapper)
            {
                _menuRepository = menuRepository;
                _mapper = mapper;
            }

            public async Task<List<MenuDto>> GetAllMenus()
            {
                var menus = await _menuRepository.GetAll()
                     .OrderByDescending(m => m.Date)
                     .ToListAsync();
                return _mapper.Map<List<MenuDto>>(menus);
            }

            public async Task<List<MenuDto>> GetMenusByTitle(string title)
            {
                var menus = await _menuRepository.GetAll()
                    .Where(m => m.Title.Contains(title))
                    .ToListAsync();
                return _mapper.Map<List<MenuDto>>(menus);
            }

            public async Task<List<MenuDto>> GetMenusByDate(DateTime date)
            {
                var menus = await _menuRepository.GetAll()
                    .Where(m => m.Date.Date == date.Date)
                    .ToListAsync();
                return _mapper.Map<List<MenuDto>>(menus);
            }

            public async Task<List<MenuDto>> GetMenusByDateAndTitle(DateTime date, string title)
            {
                var menus = await _menuRepository.GetAll()
                    .Where(m => m.Date.Date == date.Date && m.Title.Contains(title))
                    .ToListAsync();
                return _mapper.Map<List<MenuDto>>(menus);
            }
            public async Task<List<MenuDto>> GetDistinctMenus()
            {
                var allMenus = await _menuRepository.GetAll()
                   .OrderByDescending(m => m.Date).
                    ToListAsync();
                var distinctMenus = new List<Menu>();

                foreach (var menu in allMenus)
                {
                    if (!distinctMenus.Any(existing => MenuFilterHelper.AreDescriptionsSimilar(menu.Description, existing.Description)))
                    {
                        distinctMenus.Add(menu);
                    }
                }

                return _mapper.Map<List<MenuDto>>(distinctMenus);
            }
        }
    }
}
