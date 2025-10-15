namespace MenzaMate.Business.Models
{
    public class PaginatedListDto<T>
    {
        public List<T> Data { get; set; }
        public int TotalCount { get; set; }
    }
}
