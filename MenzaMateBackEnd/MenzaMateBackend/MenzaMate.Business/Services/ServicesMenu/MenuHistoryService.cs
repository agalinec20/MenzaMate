using AutoMapper;
using MenzaMate.Business.Models.ModelsMenu;
using MenzaMate.Business.Services.INameService;
using MenzaMate.Data.Entities;
using MenzaMate.Data.Generic;
using MenzaMateBackend.Data.Entities;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MenzaMate.Business.Services.ServicesMenu
{
    public class MenuHistoryService : IMenuHistoryService
    {
        private readonly IRepository<MenuHistory> _repository;
        private readonly IRepository<Menu> _menuRepository;
        private readonly IRepository<User> _userRepository;
        private readonly IMapper _mapper;

        public MenuHistoryService(
            IRepository<MenuHistory> repository,
            IRepository<Menu> menuRepository,
            IRepository<User> userRepository,
            IMapper mapper)
        {
            _repository = repository;
            _menuRepository = menuRepository;
            _userRepository = userRepository;
            _mapper = mapper;
        }

        public async Task<IEnumerable<MenuHistoryDto>> GetHistoryMenusByUserIdAsync(int userId)
        {
            var historyMenus = await _repository.GetAll()
                .Include(mh => mh.Menu)
                .Where(mh => mh.UserId == userId)
                .ToListAsync();

            return _mapper.Map<List<MenuHistoryDto>>(historyMenus);
        }

        public async Task<MenuHistoryDto> AddMenuToHistoryAsync(int userId, int menuId)
        {
            var menu = await _menuRepository.GetAll().FirstOrDefaultAsync(m => m.MenuId == menuId);
            var user = await _userRepository.GetAll().FirstOrDefaultAsync(u => u.UserId == userId);

            if (menu == null)
            {
                throw new ArgumentException("Menu not found.");
            }

            if (user == null)
            {
                throw new ArgumentException("User not found.");
            }

            var historyMenu = new MenuHistory
            {
                UserId = userId,
                MenuId = menuId,
                Added = DateTime.Now
            };

            _repository.Add(historyMenu);
            await _repository.SaveAsync();

            return _mapper.Map<MenuHistoryDto>(historyMenu);
        }
    }
}
