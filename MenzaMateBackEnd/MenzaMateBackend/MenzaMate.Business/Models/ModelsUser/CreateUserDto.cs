namespace MenzaMate.Business.Models.ModelsUser
{
    public class CreateUserDto : IUser
    {
        public string Role { get; set; } = "Student";
        public string Email { get; set; }
        public string Username { get; set; }
        public string GoogleId { get; set; }
    }

}
