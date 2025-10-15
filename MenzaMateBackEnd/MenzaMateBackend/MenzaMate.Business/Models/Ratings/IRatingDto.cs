namespace MenzaMate.Business.Models.Ratings
{
    public interface IRatingDto
    {
        int MenuId { get; set; }
        int UserId { get; set; }
        DateTime RatingDate { get; set; }
        string? Comment { get; set; }
        int MenuRating { get; set; }
    }
}
