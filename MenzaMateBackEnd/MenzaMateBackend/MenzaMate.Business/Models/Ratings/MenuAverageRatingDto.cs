using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MenzaMate.Business.Models.Ratings
{
    public class MenuAverageRatingDto
    {
        public int MenuId { get; set; }
        public double AverageRating { get; set; }
        public int RatingCount { get; set; }
        public string Title { get; set; }
        public string Description { get; set; }
    }
}
