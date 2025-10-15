namespace MenzaMate.Business.Models.ModelsMenu
{
    public class FavoriteMenuCreateDto : IFavoriteMenuDto
    {
        public int UserId { get; set; }
        public int MenuId { get; set; }
        public DateTime Added { get; set; }
    }

}
