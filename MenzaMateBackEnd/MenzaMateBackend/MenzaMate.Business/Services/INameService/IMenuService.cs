using MenzaMate.Business.Models.ModelsMenu;

namespace MenzaMate.Business.Services.INameService
{
    public interface IMenuService
    {
        Task<List<MenuDto>> GetAllMenus();
        Task<List<MenuDto>> GetMenusByTitle(string title);
        Task<List<MenuDto>> GetMenusByDate(DateTime date);
        Task<List<MenuDto>> GetMenusByDateAndTitle(DateTime date, string title);
        Task<List<MenuDto>> GetDistinctMenus();
    }

}
