using MenzaMate.Business.Models.ModelsMenu;

namespace MenzaMate.Business.Services.INameService
{
    public interface IFavoriteMenuService
    {
        Task<IEnumerable<FavoriteMenuDto>> GetFavoriteMenusByUserIdAsync(int userId);
        Task<FavoriteMenuDto> AddMenuToFavoritesAsync(int userId, int menuId);
        Task<bool> RemoveMenuFromFavoritesAsync(int userId, int menuId);
    }
}
