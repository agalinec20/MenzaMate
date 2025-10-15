using MenzaMate.Data.Entities;

namespace MenzaMate.Business.Services.INameService
{
    public interface IAuthService
    {
        Task<User> GoogleLoginAsync(string idToken);
    }
}
