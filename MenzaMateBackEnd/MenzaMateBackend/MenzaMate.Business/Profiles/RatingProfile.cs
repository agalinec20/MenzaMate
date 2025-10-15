using AutoMapper;
using MenzaMate.Business.Models.Ratings;
using MenzaMate.Data.Entities;
using MenzaMateBackend.Data.Entities;

namespace MenzaMate.Business.Profiles
{
    public class RatingProfile : Profile
    {
        public RatingProfile()
        {
            CreateMap<RatingCreateDto, Rating>()
                .ForMember(dest => dest.Comment, opt => opt.MapFrom(src => src.Comment ?? string.Empty));


             CreateMap<Rating, RatingReadDto>()
            .ForMember(dest => dest.Username, opt => opt.MapFrom(src => src.User.Username));

            CreateMap<Menu, MenuAverageRatingDto>()
                .ForMember(dest => dest.MenuId, opt => opt.MapFrom(src => src.MenuId))
                .ForMember(dest => dest.Title, opt => opt.MapFrom(src => src.Title))
                .ForMember(dest => dest.Description, opt => opt.MapFrom(src => src.Description));

        }
    }
}
