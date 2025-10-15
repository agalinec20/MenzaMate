using AutoMapper;
using MenzaMate.Business.Models;
using MenzaMate.Business.Models.Surveys;
using MenzaMate.Business.Services.INameService;
using MenzaMate.Data.Entities;
using MenzaMate.Data.Generic;
using Microsoft.EntityFrameworkCore;

namespace MenzaMate.Business.Services.ServicesSurvey
{
    public class SurveyService : ISurveyService
    {
        private readonly IRepository<Survey> _surveyRepository;
        private readonly IRepository<Question> _questionRepository;
        private readonly IRepository<Answer> _answerRepository;
        private readonly IMapper _mapper;

        public SurveyService(
         IRepository<Survey> surveyRepository,
         IRepository<Question> questionRepository,
         IRepository<Answer> answerRepository,
         IMapper mapper)
        {
            _surveyRepository = surveyRepository;
            _questionRepository = questionRepository;
            _answerRepository = answerRepository;
            _mapper = mapper;
        }

        public async Task<List<SurveyDetailDto>> GetAllSurveysAsync()
        {
            var surveys = await _surveyRepository.GetAll()
                .Include(s => s.Questions)
                .ThenInclude(q => q.Answers)
                .ToListAsync();

            return _mapper.Map<List<SurveyDetailDto>>(surveys);
        }

        public async Task<List<SurveyDetailDto>> GetSurveysByUserIdAsync(int userId)
        {
            var surveys = await _surveyRepository.GetAll()
                .Where(s => s.UserId == userId)
                .Include(s => s.Questions)
                .ThenInclude(q => q.Answers)
                .ToListAsync();

            return _mapper.Map<List<SurveyDetailDto>>(surveys);
        }

        public async Task AddSurveyAsync(SurveyDetailDto surveyDto)
        {
            var survey = _mapper.Map<Survey>(surveyDto);

            foreach (var question in survey.Questions)
            {
                question.Answers = new List<Answer>();
            }
            _surveyRepository.Add(survey);
            await _surveyRepository.SaveAsync();
        }


        public async Task AddQuestionToSurveyAsync(int surveyId, QuestionDto questionDto)
        {
            var survey = await _surveyRepository.GetAll()
                .Where(s => s.SurveyId == surveyId)
                .Include(s => s.Questions)
                .FirstOrDefaultAsync();

            if (survey == null)
                throw new KeyNotFoundException("Survey not found.");

            var question = _mapper.Map<Question>(questionDto);
            survey.Questions.Add(question);

            _surveyRepository.Update(survey);
            await _surveyRepository.SaveAsync();
        }

        public async Task AddAnswerToQuestionAsync(int questionId, AnswerDto answerDto)
        {
            var question = await _questionRepository.GetAll()
                .Where(q => q.QuestionId == questionId)
                .Include(q => q.Answers)
                .FirstOrDefaultAsync();

            if (question == null)
                throw new KeyNotFoundException("Question not found.");

            var answer = _mapper.Map<Answer>(answerDto);

            if (question.Answers == null)
                question.Answers = new List<Answer>();

            question.Answers.Add(answer);

            _questionRepository.Update(question);
            await _questionRepository.SaveAsync();
        }
        public async Task<List<AnswerDto>> GetAnswersBySurveyIdAsync(int surveyId)
        {
            var answers = await _answerRepository.GetAll()
                .Where(a => a.Question.SurveyId == surveyId)
                .Include(a => a.User)
                .Include(a => a.Question)
                .ThenInclude(q => q.Survey) 
                .ToListAsync();

            var answerDtos = _mapper.Map<List<AnswerDto>>(answers);

            foreach (var answerDto in answerDtos)
            {
                var question = answers.FirstOrDefault(a => a.AnswerId == answerDto.AnswerId)?.Question;
                if (question != null)
                {
                    answerDto.QuestionText = question.QuestionText;
                    answerDto.SurveyId = question.SurveyId;
                }
            }

            return answerDtos;
        }


        public async Task<List<AnswerDto>> GetAnswersByQuestionIdAsync(int questionId)
        {
            var answers = await _answerRepository.GetAll()
                .Where(a => a.QuestionId == questionId)
                .Include(a => a.User)
                .ToListAsync();

            return _mapper.Map<List<AnswerDto>>(answers);
        }
        public async Task<List<SurveyDetailDto>> GetAvailableSurveysForUserAsync(int userId)
        {

            var allSurveys = await _surveyRepository.GetAll()
                .Include(s => s.Questions)
                .ThenInclude(q => q.Answers)
                .ToListAsync();

            var answeredSurveys = await _answerRepository.GetAll()
                .Where(a => a.UserId == userId)
                .Include(a => a.Question)
                .ThenInclude(q => q.Survey)
                .ToListAsync();

            var answeredSurveyIds = answeredSurveys
                .Select(a => a.Question.SurveyId)
                .Distinct()
                .ToList();

            var availableSurveys = allSurveys
                .Where(survey => !answeredSurveyIds.Contains(survey.SurveyId))
                .ToList();

            return _mapper.Map<List<SurveyDetailDto>>(availableSurveys);
        }
        public async Task<List<AnswerDto>> GetAnswersByUserIdAsync(int userId)
        {
            var answers = await _answerRepository.GetAll()
                .Where(a => a.UserId == userId)
                .Include(a => a.User)
                .Include(a => a.Question)
                .ThenInclude(q => q.Survey) 
                .ToListAsync();

            var answerDtos = _mapper.Map<List<AnswerDto>>(answers);

            foreach (var answerDto in answerDtos)
            {
                var question = answers.FirstOrDefault(a => a.AnswerId == answerDto.AnswerId)?.Question;
                if (question != null && question.Survey != null)
                {
                    answerDto.SurveyId = question.Survey.SurveyId;
                }
            }

            return answerDtos;
        }
        public async Task DeleteSurveyAsync(int surveyId)
        {
            var survey = await _surveyRepository.GetAll()
                .Where(s => s.SurveyId == surveyId)
                .Include(s => s.Questions)
                .ThenInclude(q => q.Answers)
                .FirstOrDefaultAsync();

            if (survey == null)
            {
                throw new KeyNotFoundException("Survey not found.");
            }

            foreach (var question in survey.Questions)
            {
                foreach (var answer in question.Answers)
                {
                    _answerRepository.Delete(answer);
                }
                _questionRepository.Delete(question);
            }

            _surveyRepository.Delete(survey);
            await _surveyRepository.SaveAsync();
        }

    }
}

