using MenzaMate.Business.Models.Ratings;

namespace MenzaMate.Business.Services.Ratings
{
    public interface IRatingService
    {
        Task AddRatingAsync(IRatingDto ratingDto);
        Task<List<RatingReadDto>> GetRatingsByMenuIdAsync(int menuId);
        Task<(double AverageRating, int RatingCount)> GetAverageRatingByMenuIdAsync(int menuId);
        Task<List<MenuAverageRatingDto>> GetAverageRatingsForAllMenusAsync();
        Task<List<MenuAverageRatingDto>> GetTopRatedMenusAsync(int count);
        Task<List<MenuAverageRatingDto>> GetWorstRatedMenusAsync(int count);
    }
}
