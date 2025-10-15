using MenzaMate.Business.Models.ModelsMenu;
using MenzaMate.Business.Services.INameService;
using Microsoft.AspNetCore.Mvc;

namespace MenzaMateBackend.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class FavoriteMenuController : ControllerBase
    {
        private readonly IFavoriteMenuService _favoriteMenuService;

        public FavoriteMenuController(IFavoriteMenuService favoriteMenuService)
        {
            _favoriteMenuService = favoriteMenuService;
        }
        [HttpGet("{userId}")]
        public async Task<IActionResult> GetFavoriteMenus(int userId)
        {
            var result = await _favoriteMenuService.GetFavoriteMenusByUserIdAsync(userId);
            return Ok(result);
        }

        [HttpPost]
        public async Task<IActionResult> AddMenuToFavorites([FromBody] FavoriteMenuCreateDto favoriteMenuCreateDto)
        {
            var result = await _favoriteMenuService.AddMenuToFavoritesAsync(favoriteMenuCreateDto.UserId, favoriteMenuCreateDto.MenuId);
            return CreatedAtAction(nameof(GetFavoriteMenus), new { userId = favoriteMenuCreateDto.UserId }, result);
        }
        [HttpDelete("{userId}/{menuId}")]
        public async Task<IActionResult> RemoveMenuFromFavorites(int userId, int menuId)
        {
            var result = await _favoriteMenuService.RemoveMenuFromFavoritesAsync(userId, menuId);
            if (!result)
            {
                return NotFound("Menu not found in favorites.");
            }
            return NoContent();
        }
    }
}
