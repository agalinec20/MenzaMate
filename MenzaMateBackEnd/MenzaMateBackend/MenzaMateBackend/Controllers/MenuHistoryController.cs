using MenzaMate.Business.Models.ModelsMenu;
using MenzaMate.Business.Services;
using MenzaMate.Business.Services.INameService;
using Microsoft.AspNetCore.Mvc;

namespace MenzaMateBackend.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class MenuHistoryController : ControllerBase
    {
        private readonly IMenuHistoryService _menuHistoryService;

        public MenuHistoryController(IMenuHistoryService menuHistoryService)
        {
            _menuHistoryService = menuHistoryService;
        }

        [HttpGet("{userId}")]
        public async Task<IActionResult> GetHistoryMenus(int userId)
        {
            var result = await _menuHistoryService.GetHistoryMenusByUserIdAsync(userId);
            return Ok(result);
        }

        [HttpPost]
        public async Task<IActionResult> AddMenuToHistory([FromBody] HistoryMenuCreateDto request)
        {
            try
            {
                var result = await _menuHistoryService.AddMenuToHistoryAsync(request.UserId, request.MenuId);
                return Ok(result);
            }
            catch (ArgumentException ex)
            {
                return BadRequest(new { error = ex.Message });
            }
        }
    }

}
