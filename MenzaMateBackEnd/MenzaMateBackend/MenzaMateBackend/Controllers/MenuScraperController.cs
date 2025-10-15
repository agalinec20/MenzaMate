using MenzaMate.Business.Services.INameService;
using Microsoft.AspNetCore.Mvc;

namespace MenzaMateBackend.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class MenuScraperController : ControllerBase
    {
        private readonly IMenuScraperService _menuScraperService;

        public MenuScraperController(IMenuScraperService menuScraperService)
        {
            _menuScraperService = menuScraperService;
        }
        [HttpPost("scrape-and-save")]
        public async Task<IActionResult> ScrapeAndSaveMenus()
        {
            try
            {
                await _menuScraperService.ScrapeMenusAndSave();

                return Ok("Scraping completed successfully.");
            }
            catch (Exception ex)
            {
                return BadRequest($"An error occurred: {ex.Message}");
            }
        }

    }
}

