using MenzaMate.Business.Services.INameService;
using Microsoft.AspNetCore.Mvc;

namespace MenzaMateBackend.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class MenuController : ControllerBase
    {
        private readonly IMenuService _menuService;

        public MenuController(IMenuService menuService)
        {
            _menuService = menuService;
        }

        [HttpGet("all")]
        public async Task<IActionResult> GetAllMenus()
        {
            try
            {
                var menus = await _menuService.GetAllMenus();
                return Ok(menus);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        [HttpGet("by-title/{title}")]
        public async Task<IActionResult> GetMenusByTitle(string title)
        {
            try
            {
                var menus = await _menuService.GetMenusByTitle(title);
                return Ok(menus);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        [HttpGet("by-date/{date}")]
        public async Task<IActionResult> GetMenusByDate(DateTime date)
        {
            try
            {
                var menus = await _menuService.GetMenusByDate(date);
                return Ok(menus);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        [HttpGet("by-date-and-title/{date}/{title}")]
        public async Task<IActionResult> GetMenusByDateAndTitle(DateTime date, string title)
        {
            try
            {
                var menus = await _menuService.GetMenusByDateAndTitle(date, title);
                return Ok(menus);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }
        [HttpGet("distinct")]
        public async Task<IActionResult> GetDistinctMenus()
        {
            try
            {
                var menus = await _menuService.GetDistinctMenus();
                return Ok(menus);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }
    }
}
