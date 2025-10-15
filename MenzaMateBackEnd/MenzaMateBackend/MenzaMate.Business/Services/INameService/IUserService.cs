using MenzaMate.Business.Models.ModelsUser;
using MenzaMate.Business.Models.Surveys;

namespace MenzaMate.Business.Services.INameService
{
    public interface IUserService
    {
        Task<List<UserDto>> GetAllUsers();
        Task<UserDto> GetUserByIdAsync(int id);
        Task<UserDto> AddUserAsync(CreateUserDto user);
        Task UpdateUserAsync(UserDto user);
        Task DeleteUserAsync(UserDto user);
        Task<UserDto> AddOrGetUserAsync(GoogleUserDto googleUser);

    }

}
