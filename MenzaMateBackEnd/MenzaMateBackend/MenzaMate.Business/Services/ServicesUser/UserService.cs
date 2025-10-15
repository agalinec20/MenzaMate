using AutoMapper;
using MenzaMate.Business.Models.ModelsUser;
using MenzaMate.Business.Services.INameService;
using MenzaMate.Data.Entities;
using MenzaMate.Data.Enums;
using MenzaMate.Data.Generic;
using Microsoft.EntityFrameworkCore;

namespace MenzaMate.Business.Services.ServicesUser
{
    public class UserService : IUserService
    {
        private readonly IRepository<User> _userRepository;
        private readonly IMapper _mapper;

        public UserService(IRepository<User> userRepository, IMapper mapper)
        {
            _userRepository = userRepository;
            _mapper = mapper;
        }

        public async Task<List<UserDto>> GetAllUsers()
        {
            var users = await _userRepository.GetAll().ToListAsync();
            return _mapper.Map<List<UserDto>>(users);
        }

        public async Task<UserDto> GetUserByIdAsync(int id)
        {
            var user = await _userRepository.GetByIdAsync(id);
            return _mapper.Map<UserDto>(user);
        }

        public async Task<UserDto> AddUserAsync(CreateUserDto createUserDto)
        {
            var userEntity = _mapper.Map<User>(createUserDto);
            _userRepository.Add(userEntity);
            await _userRepository.SaveAsync();
            var createdUser = _mapper.Map<UserDto>(userEntity);
            return createdUser;
        }


        public async Task UpdateUserAsync(UserDto user)
        {
            var userEntity = _mapper.Map<User>(user);
            _userRepository.Update(userEntity);
            await _userRepository.SaveAsync();
        }

        public async Task DeleteUserAsync(UserDto user)
        {
            var userEntity = _mapper.Map<User>(user);
            _userRepository.Delete(userEntity);
            await _userRepository.SaveAsync();
        }
        public async Task<UserDto> AddOrGetUserAsync(GoogleUserDto googleUser)
        {
            var existingUser = await _userRepository.GetAll()
                .FirstOrDefaultAsync(u => u.Email == googleUser.Email);

            if (existingUser != null)
            {
                return _mapper.Map<UserDto>(existingUser);
            }

            var newUser = new User
            {
                Username = googleUser.Name,
                Email = googleUser.Email,
                GoogleId = googleUser.GoogleId,
                Role = RolesEnum.Student.ToString()
            };

            _userRepository.Add(newUser);
            await _userRepository.SaveAsync();

            return _mapper.Map<UserDto>(newUser);
        }

    }
}
