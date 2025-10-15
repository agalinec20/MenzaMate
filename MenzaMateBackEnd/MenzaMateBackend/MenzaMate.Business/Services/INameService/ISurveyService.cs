using MenzaMate.Business.Models;
using MenzaMate.Business.Models.Surveys;

namespace MenzaMate.Business.Services.INameService
{
    public interface ISurveyService
    {
        Task<List<SurveyDetailDto>> GetAllSurveysAsync();
        Task<List<SurveyDetailDto>> GetSurveysByUserIdAsync(int userId);
        Task AddSurveyAsync(SurveyDetailDto surveyDto);
        Task AddQuestionToSurveyAsync(int surveyId, QuestionDto questionDto);
        Task AddAnswerToQuestionAsync(int questionId, AnswerDto answerDto);
        Task<List<AnswerDto>> GetAnswersBySurveyIdAsync(int surveyId);
        Task<List<AnswerDto>> GetAnswersByQuestionIdAsync(int questionId);

        Task<List<SurveyDetailDto>> GetAvailableSurveysForUserAsync(int userId);

        Task<List<AnswerDto>> GetAnswersByUserIdAsync(int userId);

        Task DeleteSurveyAsync(int surveyId);

    }

}
