using MenzaMate.Business.Services.INameService;
using MenzaMate.Business.Services.MenzaMate.Business.Services;
using MenzaMate.Business.Services.Ratings;
using MenzaMate.Business.Services.ServicesAuth;
using MenzaMate.Business.Services.ServicesMenu;
using MenzaMate.Business.Services.ServicesSurvey;
using MenzaMate.Business.Services.ServicesUser;
using MenzaMate.Data.Entities;
using MenzaMate.Data.Generic;
using MenzaMate.Data.Stores;
using MenzaMateBackend.Data.Entities;
using Microsoft.EntityFrameworkCore;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.

builder.Services.AddDbContext<MenzaMateContext>(options =>
    options.UseSqlServer(builder.Configuration.GetConnectionString("MenzaMateDb"))
           .EnableSensitiveDataLogging()  // Enable sensitive data logging
           .LogTo(Console.WriteLine, Microsoft.Extensions.Logging.LogLevel.Information));  // Log to console

// AutoMapper Configuration
builder.Services.AddAutoMapper(AppDomain.CurrentDomain.GetAssemblies());

// Register repositories
builder.Services.AddScoped<IRepository<FavoriteMenu>, Repository<FavoriteMenu>>();
builder.Services.AddScoped<IRepository<Menu>, Repository<Menu>>();
builder.Services.AddScoped<IRepository<User>, Repository<User>>();
builder.Services.AddScoped<IUserService, UserService>();
builder.Services.AddScoped<IRepository<Survey>, Repository<Survey>>();       
builder.Services.AddScoped<IRepository<Answer>, Repository<Answer>>();
builder.Services.AddScoped<IRepository<Question>, Repository<Question>>();
builder.Services.AddScoped<IRepository<MenuHistory>, Repository<MenuHistory>>();


// Register services
builder.Services.AddScoped<ISurveyService, SurveyService>();
builder.Services.AddScoped<IFavoriteMenuService, FavoriteMenuService>();
builder.Services.AddScoped<IRatingService, RatingService>();
builder.Services.AddScoped<IMenuService, MenuService>();
builder.Services.AddHttpClient<IMenuScraperService, MenuScraperService>();
builder.Services.AddScoped<IRepository<Rating>, Repository<Rating>>();
builder.Services.AddScoped<IMenuHistoryService, MenuHistoryService>();





builder.Services.AddControllers();
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

// Add Google services
builder.Services.AddScoped<IGoogleAuthService, GoogleAuthService>();
builder.Services.AddScoped<IUserRepository, UserRepository>();
builder.Services.AddScoped<IGoogleAuthService, GoogleAuthService>();
builder.Services.AddScoped<IAuthService, AuthService>();

var app = builder.Build();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseHttpsRedirection();

app.UseAuthorization();

app.MapControllers();

app.Run();
