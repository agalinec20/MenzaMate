using AutoMapper;
using MenzaMate.Business.Models.ModelsMenu;
using MenzaMate.Data.Entities;

namespace MenzaMate.Business.Profiles
{
    public class FavoriteMenuProfile : Profile
    {
        public FavoriteMenuProfile()
        {
            CreateMap<FavoriteMenu, FavoriteMenuDto>()
                .ForMember(dest => dest.MenuTitle, opt => opt.MapFrom(src => src.Menu.Title))
                .ForMember(dest => dest.MenuDescription, opt => opt.MapFrom(src => src.Menu.Description));

            CreateMap<FavoriteMenuCreateDto, FavoriteMenu>();
        }
    }
}
