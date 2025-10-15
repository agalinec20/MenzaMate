using AutoMapper;
using MenzaMate.Business.Models.ModelsMenu;
using MenzaMate.Business.Services.INameService;
using MenzaMate.Data.Entities;
using MenzaMate.Data.Generic;
using MenzaMateBackend.Data.Entities;
using Microsoft.EntityFrameworkCore;

namespace MenzaMate.Business.Services.ServicesMenu
{
    public class FavoriteMenuService : IFavoriteMenuService
    {
        private readonly IRepository<FavoriteMenu> _repository;
        private readonly IRepository<Menu> _menuRepository;
        private readonly IRepository<User> _userRepository;
        private readonly IMapper _mapper;

        public FavoriteMenuService(
            IRepository<FavoriteMenu> repository,
            IRepository<Menu> menuRepository,
            IRepository<User> userRepository,
            IMapper mapper)
        {
            _repository = repository;
            _menuRepository = menuRepository;
            _userRepository = userRepository;
            _mapper = mapper;
        }

        public async Task<IEnumerable<FavoriteMenuDto>> GetFavoriteMenusByUserIdAsync(int userId)
        {
            var favoriteMenus = await _repository.GetAll()
                .Include(fm => fm.Menu)
                .Where(fm => fm.UserId == userId)
                .ToListAsync();

            return _mapper.Map<List<FavoriteMenuDto>>(favoriteMenus);
        }

        public async Task<FavoriteMenuDto> AddMenuToFavoritesAsync(int userId, int menuId)
        {
            var menu = await _menuRepository.GetAll().FirstOrDefaultAsync(m => m.MenuId == menuId);
            var user = await _userRepository.GetAll().FirstOrDefaultAsync(u => u.UserId == userId);

            if (menu == null || user == null)
            {
                throw new ArgumentException("Menu or User not found.");
            }

            var favoriteMenu = new FavoriteMenu
            {
                UserId = userId,
                MenuId = menuId,
                Added = DateTime.Now
            };

            _repository.Add(favoriteMenu);
            await _repository.SaveAsync();

            return _mapper.Map<FavoriteMenuDto>(favoriteMenu);
        }
        public async Task<bool> RemoveMenuFromFavoritesAsync(int userId, int menuId)
        {
            var favoriteMenu = await _repository.GetAll()
                .FirstOrDefaultAsync(fm => fm.UserId == userId && fm.MenuId == menuId);

            if (favoriteMenu == null)
            {
                return false;
            }

            _repository.Delete(favoriteMenu);
            await _repository.SaveAsync();

            return true;
        }
    }
}
