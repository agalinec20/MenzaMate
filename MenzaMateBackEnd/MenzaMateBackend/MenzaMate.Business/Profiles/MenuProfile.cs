using AutoMapper;
using MenzaMate.Business.Models.ModelsMenu;
using MenzaMateBackend.Data.Entities;

namespace MenzaMate.Business.Profiles
{
    public class MenuProfile : Profile
    {
        public MenuProfile()
        {
            CreateMap<Menu, MenuDto>()
                .ForMember(dest => dest.MenuId, opt => opt.MapFrom(src => src.MenuId))
                .ForMember(dest => dest.Title, opt => opt.MapFrom(src => src.Title))
                .ForMember(dest => dest.Description, opt => opt.MapFrom(src => src.Description))
                .ForMember(dest => dest.CreatedDate, opt => opt.MapFrom(src => src.Date));

            CreateMap<MenuScraperDto, MenuDto>()
                .ForMember(dest => dest.Title, opt => opt.MapFrom(src => src.Title))
                .ForMember(dest => dest.Description, opt => opt.MapFrom(src => src.Description))
                .ForMember(dest => dest.CreatedDate, opt => opt.MapFrom(src => src.CreatedDate));

            CreateMap<MenuScraperDto, Menu>()
                .ForMember(dest => dest.Title, opt => opt.MapFrom(src => src.Title))
                .ForMember(dest => dest.Description, opt => opt.MapFrom(src => src.Description))
                .ForMember(dest => dest.Date, opt => opt.MapFrom(src => src.CreatedDate))
                .ForMember(dest => dest.MenuId, opt => opt.Ignore());
        }
    }
}
