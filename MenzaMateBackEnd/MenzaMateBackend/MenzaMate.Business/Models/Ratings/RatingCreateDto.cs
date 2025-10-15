namespace MenzaMate.Business.Models.Ratings
{
    public class RatingCreateDto : IRatingDto
    {
        public int MenuId { get; set; }
        public int UserId { get; set; }
        public DateTime RatingDate { get; set; }
        public string? Comment { get; set; }
        public int MenuRating { get; set; }
    }
}
