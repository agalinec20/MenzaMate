namespace MenzaMate.Business.Models.ModelsMenu
{
    public class FavoriteMenuDto : IFavoriteMenuDto
    {
        public int Id { get; set; }
        public int UserId { get; set; }
        public int MenuId { get; set; }
        public DateTime Added { get; set; }

        public string MenuTitle { get; set; }
        public string MenuDescription { get; set; }
    }
}
