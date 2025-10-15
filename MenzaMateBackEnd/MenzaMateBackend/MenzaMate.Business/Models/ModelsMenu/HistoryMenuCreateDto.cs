using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MenzaMate.Business.Models.ModelsMenu
{
    public class HistoryMenuCreateDto : IMenuHistoryDto
    {
        public int UserId { get; set; }
        public int MenuId { get; set; }
        public DateTime Viewed { get; set; }
    }
}
