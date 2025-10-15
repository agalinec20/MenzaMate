using AutoMapper;
using MenzaMate.Business.Models.ModelsMenu;
using MenzaMate.Data.Entities;
using MenzaMate.Data.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MenzaMate.Business.Profiles
{
    public class MenuHistoryProfile : Profile
    {
        public MenuHistoryProfile()
        {
            CreateMap<MenuHistory, MenuHistoryDto>()
                .ForMember(dest => dest.MenuTitle, opt => opt.MapFrom(src => src.Menu.Title))
                .ForMember(dest => dest.MenuDescription, opt => opt.MapFrom(src => src.Menu.Description))
                .ForMember(dest => dest.Viewed, opt => opt.MapFrom(src => src.Added));

            CreateMap<HistoryMenuCreateDto, MenuHistory>();
        }
    }

}
