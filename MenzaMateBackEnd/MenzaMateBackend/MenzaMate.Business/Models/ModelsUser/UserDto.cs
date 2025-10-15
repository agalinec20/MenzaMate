namespace MenzaMate.Business.Models.ModelsUser
{
    public class UserDto
    {
        public int UserId { get; set; }
        public string Role { get; set; } = "Student";
        public string Email { get; set; }
        public string Username { get; set; }
        public string GoogleId { get; set; }
    }
}
