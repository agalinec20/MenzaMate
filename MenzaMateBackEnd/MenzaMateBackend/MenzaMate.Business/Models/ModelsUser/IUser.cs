namespace MenzaMate.Business.Models.ModelsUser
{
    public interface IUser
    {
        string Role { get; set; }
        string Email { get; set; }
        string Username { get; set; }
        string GoogleId { get; set; }
    }
}
