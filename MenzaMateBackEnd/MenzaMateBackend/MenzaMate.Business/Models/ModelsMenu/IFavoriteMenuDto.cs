namespace MenzaMate.Business.Models.ModelsMenu
{
    public interface IFavoriteMenuDto
    {
        int UserId { get; set; }
        int MenuId { get; set; }
        DateTime Added { get; set; }
    }
}
