using MenzaMate.Data.Entities;
using MenzaMate.Data.Stores;
using Microsoft.EntityFrameworkCore;

namespace MenzaMate.Data.Generic
{
    public class UserRepository : Repository<User>, IUserRepository
    {
        private readonly MenzaMateContext _dbContext;

        public UserRepository(MenzaMateContext dbContext) : base(dbContext)
        {
            _dbContext = dbContext;
        }

        public async Task<User?> GetByGoogleIdAsync(string googleId)
        {
            return await _dbContext.Users.FirstOrDefaultAsync(u => u.GoogleId == googleId);
        }

        public async Task<User> CreateAsync(User user)
        {
            if (string.IsNullOrWhiteSpace(user.GoogleId))
            {
                user.Role = "Student";
            }

            _dbContext.Users.Add(user);
            await _dbContext.SaveChangesAsync();
            return user;
        }
    }
}
