using MenzaMate.Business.Models;
using MenzaMate.Business.Models.Surveys;
using MenzaMate.Business.Services.INameService;
using Microsoft.AspNetCore.Mvc;

namespace MenzaMateBackend.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class SurveyController : ControllerBase
    {
        private readonly ISurveyService _surveyService;

        public SurveyController(ISurveyService surveyService)
        {
            _surveyService = surveyService;
        }

        [HttpGet]
        public async Task<IActionResult> GetAllSurveys()
        {
            var surveys = await _surveyService.GetAllSurveysAsync();
            return Ok(surveys);
        }

        [HttpGet("user/{userId}")]
        public async Task<IActionResult> GetSurveysByUserId(int userId)
        {
            var surveys = await _surveyService.GetSurveysByUserIdAsync(userId);
            return Ok(surveys);
        }

        [HttpPost]
        public async Task<IActionResult> AddSurvey([FromBody] SurveyDetailDto surveyDto)
        {
            try
            {
                await _surveyService.AddSurveyAsync(surveyDto);
                return CreatedAtAction(nameof(GetAllSurveys), new { id = surveyDto.SurveyId }, surveyDto);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        [HttpPost("{surveyId}/questions")]
        public async Task<IActionResult> AddQuestionToSurvey(int surveyId, [FromBody] QuestionDto questionDto)
        {
            try
            {
                await _surveyService.AddQuestionToSurveyAsync(surveyId, questionDto);
                return Ok("Question added successfully.");
            }
            catch (KeyNotFoundException ex)
            {
                return NotFound(ex.Message);
            }
        }

        [HttpPost("questions/{questionId}/answers")]
        public async Task<IActionResult> AddAnswerToQuestion(int questionId, [FromBody] AnswerDto answerDto)
        {
            try
            {
                await _surveyService.AddAnswerToQuestionAsync(questionId, answerDto);
                return Ok("Answer added successfully.");
            }
            catch (KeyNotFoundException ex)
            {
                return NotFound(ex.Message);
            }
        }
        [HttpGet("{surveyId}/answers")]
        public async Task<IActionResult> GetAnswersBySurveyId(int surveyId)
        {
            var answers = await _surveyService.GetAnswersBySurveyIdAsync(surveyId);
            return Ok(answers);
        }


        [HttpGet("questions/{questionId}/answers")]
        public async Task<IActionResult> GetAnswersByQuestionId(int questionId)
        {
            var answers = await _surveyService.GetAnswersByQuestionIdAsync(questionId);
            return Ok(answers);
        }
        [HttpGet("available/{userId}")]
        public async Task<IActionResult> GetAvailableSurveysForUser(int userId)
        {
            try
            {
                var availableSurveys = await _surveyService.GetAvailableSurveysForUserAsync(userId);
                return Ok(availableSurveys);
            }
            catch (Exception ex)
            {
                return BadRequest($"Error fetching available surveys: {ex.Message}");
            }
        }
        [HttpGet("answers/{userId}")]
        public async Task<IActionResult> GetAnswersByUserId(int userId)
        {
            try
            {
                var answers = await _surveyService.GetAnswersByUserIdAsync(userId);
                return Ok(answers);
            }
            catch (Exception ex)
            {
                return BadRequest($"Error fetching answers: {ex.Message}");
            }
        }
        [HttpDelete("{surveyId}")]
        public async Task<IActionResult> DeleteSurvey(int surveyId)
        {
            try
            {
                await _surveyService.DeleteSurveyAsync(surveyId);
                return NoContent(); 
            }
            catch (KeyNotFoundException ex)
            {
                return NotFound(ex.Message);
            }
            catch (Exception ex)
            {
                return BadRequest($"Error deleting survey: {ex.Message}");
            }
        }

    }
}