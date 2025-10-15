namespace MenzaMate.Business.Models.Ratings
{
    public class RatingReadDto : IRatingDto
    {
        public int RatingId { get; set; }
        public int MenuId { get; set; }
        public int UserId { get; set; }
        public string Username { get; set; }
        public DateTime RatingDate { get; set; }
        public string? Comment { get; set; }
        public int MenuRating { get; set; }
    }
}
