using AutoMapper;
using MenzaMate.Business.Models.ModelsUser;
using MenzaMate.Data.Entities;

namespace MenzaMate.Business.Profiles
{
    public class UserProfile : Profile
    {
        public UserProfile()
        {
            CreateMap<User, UserDto>().ReverseMap();
            CreateMap<User, CreateUserDto>().ReverseMap();
        }
    }
}
