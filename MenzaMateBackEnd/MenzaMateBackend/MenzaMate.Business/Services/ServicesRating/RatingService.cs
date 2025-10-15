using AutoMapper;
using MenzaMate.Business.Models.Ratings;
using MenzaMate.Data.Entities;
using MenzaMate.Data.Generic;
using MenzaMateBackend.Data.Entities;
using Microsoft.EntityFrameworkCore;

namespace MenzaMate.Business.Services.Ratings
{
    public class RatingService : IRatingService
    {
        private readonly IRepository<Rating> _ratingRepository;
        private readonly IRepository<Menu> _menuRepository; 
        private readonly IMapper _mapper;

        public RatingService(IRepository<Rating> ratingRepository, IRepository<Menu> menuRepository, IMapper mapper)
        {
            _ratingRepository = ratingRepository;
            _menuRepository = menuRepository;  
            _mapper = mapper;
        }

        public async Task AddRatingAsync(IRatingDto ratingDto)
        {
            var menuExists = await _menuRepository.GetAll()
                .AnyAsync(m => m.MenuId == ratingDto.MenuId);

            if (!menuExists)
            {
                throw new ArgumentException("MenuId does not exist.");
            }

            var rating = _mapper.Map<Rating>(ratingDto);
            _ratingRepository.Add(rating);
            await _ratingRepository.SaveAsync();
        }

        public async Task<List<RatingReadDto>> GetRatingsByMenuIdAsync(int menuId)
        {
            var ratings = await _ratingRepository.GetAll()
                .Include(r => r.User)
                .Where(r => r.MenuId == menuId)
                .ToListAsync();

            return _mapper.Map<List<RatingReadDto>>(ratings);
        }


        public async Task<(double AverageRating, int RatingCount)> GetAverageRatingByMenuIdAsync(int menuId)
        {
            var ratings = _ratingRepository.GetAll()
                .Where(r => r.MenuId == menuId);

            double averageRating = ratings.Any() ? ratings.Average(r => r.MenuRating) : 0.0;
            int ratingCount = ratings.Count();

            return (averageRating, ratingCount);
        }

        public async Task<List<MenuAverageRatingDto>> GetAverageRatingsForAllMenusAsync()
        {

            var ratings = await _ratingRepository.GetAll()
                .GroupBy(r => r.MenuId)
                .Select(g => new
                {
                    MenuId = g.Key,
                    AverageRating = g.Any() ? g.Average(r => r.MenuRating) : 0.0,
                    RatingCount = g.Count()
                })
                .ToListAsync();

        
            var menuIds = ratings.Select(r => r.MenuId).ToList();
            var menus = await _menuRepository.GetAll()
                .Where(m => menuIds.Contains(m.MenuId))
                .ToListAsync();
            var result = menus.Select(menu =>
            {
                var rating = ratings.FirstOrDefault(r => r.MenuId == menu.MenuId);
                var dto = _mapper.Map<MenuAverageRatingDto>(menu);
                dto.AverageRating = rating?.AverageRating ?? 0.0;
                dto.RatingCount = rating?.RatingCount ?? 0;   
                return dto;
            }).OrderByDescending(dto => dto.AverageRating) 
              .ToList();

            return result;
        }


        public async Task<List<MenuAverageRatingDto>> GetTopRatedMenusAsync(int count)
        {
            var allMenus = await GetAverageRatingsForAllMenusAsync();
            return allMenus.Take(count).ToList();
        }

        public async Task<List<MenuAverageRatingDto>> GetWorstRatedMenusAsync(int count)
        {
            var allMenus = await GetAverageRatingsForAllMenusAsync();
            return allMenus.OrderBy(r => r.AverageRating).Take(count).ToList();
        }
    }
}
