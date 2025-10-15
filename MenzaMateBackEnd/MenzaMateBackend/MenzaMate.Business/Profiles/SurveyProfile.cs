using AutoMapper;
using MenzaMate.Business.Models;
using MenzaMate.Business.Models.Surveys;
using MenzaMate.Data.Entities;

namespace MenzaMate.Business.Profiles
{
    public class SurveyProfile : Profile
    {
        public SurveyProfile()
        {
            CreateMap<Survey, SurveyDto>();
            CreateMap<Survey, SurveyDetailDto>();

            CreateMap<Question, QuestionDto>()
                .ForMember(dest => dest.Answers, opt => opt.Ignore()); 

            CreateMap<Answer, AnswerDto>()
                .ForMember(dest => dest.AnswerId, opt => opt.MapFrom(src => src.AnswerId))
                .ForMember(dest => dest.Responses, opt => opt.MapFrom(src => src.Responses))
                .ForMember(dest => dest.Answered, opt => opt.MapFrom(src => src.Answered))
                .ForMember(dest => dest.QuestionId, opt => opt.MapFrom(src => src.QuestionId))
                .ForMember(dest => dest.SurveyId, opt => opt.MapFrom(src => src.Question.Survey.SurveyId))
                .ForMember(dest => dest.QuestionText, opt => opt.MapFrom(src => src.Question.QuestionText));

            CreateMap<SurveyDto, Survey>();
            CreateMap<SurveyDetailDto, Survey>();
            CreateMap<QuestionDto, Question>()
                .ForMember(dest => dest.Answers, opt => opt.Ignore());
            CreateMap<AnswerDto, Answer>()
                .ForMember(dest => dest.AnswerId, opt => opt.MapFrom(src => src.AnswerId))
                .ForMember(dest => dest.Responses, opt => opt.MapFrom(src => src.Responses))
                .ForMember(dest => dest.Answered, opt => opt.MapFrom(src => src.Answered));
        }
    }
}
