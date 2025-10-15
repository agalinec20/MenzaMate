namespace MenzaMate.Business.Models.Surveys
{
    public interface ISurveyDto
    {
        int SurveyId { get; set; }
        string SurveyName { get; set; }
        DateTime SurveyDate { get; set; }
    }
}
