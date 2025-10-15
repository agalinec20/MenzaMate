using MenzaMate.Data.Entities;

namespace MenzaMate.Data.Generic
{
    public interface IUserRepository : IRepository<User>
    {
        Task<User?> GetByGoogleIdAsync(string googleId);
        Task<User> CreateAsync(User user);
    }
}
