using MenzaMate.Business.Models.ModelsMenu;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MenzaMate.Business.Services.INameService
{
    public interface IMenuHistoryService
    {
        Task<IEnumerable<MenuHistoryDto>> GetHistoryMenusByUserIdAsync(int userId);
        Task<MenuHistoryDto> AddMenuToHistoryAsync(int userId, int menuId);
    }
}
