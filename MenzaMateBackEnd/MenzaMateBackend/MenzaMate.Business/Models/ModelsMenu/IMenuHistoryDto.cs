using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MenzaMate.Business.Models.ModelsMenu
{
    public interface IMenuHistoryDto
    {
        int UserId { get; set; }
        int MenuId { get; set; }
        DateTime Viewed { get; set; }
    }
}
