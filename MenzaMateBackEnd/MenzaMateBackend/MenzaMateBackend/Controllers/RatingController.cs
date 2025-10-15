using MenzaMate.Business.Models.Ratings;
using MenzaMate.Business.Services.Ratings;
using Microsoft.AspNetCore.Mvc;

namespace MenzaMateBackend.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class RatingController : ControllerBase
    {
        private readonly IRatingService _ratingService;

        public RatingController(IRatingService ratingService)
        {
            _ratingService = ratingService;
        }

        [HttpPost]
        public async Task<IActionResult> AddRating([FromBody] RatingCreateDto ratingDto)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            await _ratingService.AddRatingAsync(ratingDto);
            return CreatedAtAction(nameof(GetRatingsByMenuId), new { menuId = ratingDto.MenuId }, ratingDto);
        }

        [HttpGet("{menuId}")]
        public async Task<IActionResult> GetRatingsByMenuId(int menuId)
        {
            var ratings = await _ratingService.GetRatingsByMenuIdAsync(menuId);
            return Ok(ratings);
        }

        [HttpGet("average/{menuId}")]
        public async Task<IActionResult> GetAverageRating(int menuId)
        {
            var (averageRating, ratingCount) = await _ratingService.GetAverageRatingByMenuIdAsync(menuId);
            return Ok(new { AverageRating = averageRating, RatingCount = ratingCount });
        }
        [HttpGet("top/{count}")]
        public async Task<IActionResult> GetTopRatedMenus(int count)
        {
            var topMenus = await _ratingService.GetTopRatedMenusAsync(count);
            return Ok(topMenus);
        }
        [HttpGet("worst/{count}")]
        public async Task<IActionResult> GetWorstRatedMenus(int count)
        {
            var worstMenus = await _ratingService.GetWorstRatedMenusAsync(count);
            return Ok(worstMenus);
        }

    }
}
