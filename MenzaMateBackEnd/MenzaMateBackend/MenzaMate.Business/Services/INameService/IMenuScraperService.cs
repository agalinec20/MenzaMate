using MenzaMate.Business.Models.ModelsMenu;

namespace MenzaMate.Business.Services.INameService
{
    public interface IMenuScraperService
    {
        Task<List<MenuScraperDto>> ScrapeMenus(string url);
        Task ScrapeMenusAndSave();
    }
}
