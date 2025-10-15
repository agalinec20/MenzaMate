using MenzaMate.Data.Entities;
using MenzaMateBackend.Data.Entities;
using Microsoft.EntityFrameworkCore;
using System.Reflection;

namespace MenzaMate.Data.Stores
{
    public class MenzaMateContext : DbContext
    {
        public MenzaMateContext(DbContextOptions options) : base(options) { }

        public DbSet<User> Users { get; set; }
        public DbSet<Survey> Surveys { get; set; }
        public DbSet<Question> Questions { get; set; } 
        public DbSet<Answer> Answers { get; set; }
        public DbSet<Menu> Menus { get; set; }
        public DbSet<Rating> Ratings { get; set; }
        public DbSet<FavoriteMenu> FavoriteMenus { get; set; }
        public DbSet<MenuHistory> MenuHistories { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);
            modelBuilder.ApplyConfigurationsFromAssembly(Assembly.GetExecutingAssembly());

            modelBuilder.Entity<Survey>()
                .HasOne(s => s.User)
                .WithMany(u => u.Surveys)
                .HasForeignKey(s => s.UserId)
                .OnDelete(DeleteBehavior.Cascade);

            modelBuilder.Entity<Question>()
                .HasOne(q => q.Survey)
                .WithMany(s => s.Questions)
                .HasForeignKey(q => q.SurveyId)
                .OnDelete(DeleteBehavior.Cascade);

            modelBuilder.Entity<Answer>()
                .HasOne(a => a.Question)
                .WithMany(q => q.Answers)
                .HasForeignKey(a => a.QuestionId)
                .OnDelete(DeleteBehavior.Cascade);

            modelBuilder.Entity<Answer>()
                .HasOne(a => a.User)
                .WithMany(u => u.Answers)
                .HasForeignKey(a => a.UserId)
                .OnDelete(DeleteBehavior.Restrict);

            modelBuilder.Entity<Rating>()
                .HasOne(r => r.User)
                .WithMany(u => u.Ratings)
                .HasForeignKey(r => r.UserId)
                .OnDelete(DeleteBehavior.Cascade);

            modelBuilder.Entity<Rating>()
                .HasOne(r => r.Menu)
                .WithMany(m => m.Ratings)
                .HasForeignKey(r => r.MenuId)
                .OnDelete(DeleteBehavior.Cascade);

            modelBuilder.Entity<Menu>()
                .HasOne(m => m.MenuHistory)
                .WithMany(h => h.Menus)
                .HasForeignKey(m => m.HistoryId)
                .OnDelete(DeleteBehavior.Cascade);

            modelBuilder.Entity<FavoriteMenu>()
                .HasOne(fm => fm.User)
                .WithMany(u => u.FavoriteMenus)
                .HasForeignKey(fm => fm.UserId)
                .OnDelete(DeleteBehavior.Cascade);

            modelBuilder.Entity<FavoriteMenu>()
                .HasOne(fm => fm.Menu)
                .WithMany(m => m.FavoriteMenus)
                .HasForeignKey(fm => fm.MenuId)
                .OnDelete(DeleteBehavior.Cascade);

            modelBuilder.Entity<Question>()
              .HasOne(q => q.Survey)
              .WithMany(s => s.Questions)
              .HasForeignKey(q => q.SurveyId)
              .OnDelete(DeleteBehavior.Cascade);

            modelBuilder.Entity<Answer>()
             .HasOne(a => a.User)
             .WithMany(u => u.Answers)
             .HasForeignKey(a => a.UserId)
             .OnDelete(DeleteBehavior.Restrict);

            modelBuilder.Entity<Answer>()
                .HasOne(a => a.Question)
                .WithMany(q => q.Answers)
                .HasForeignKey(a => a.QuestionId)
                .OnDelete(DeleteBehavior.Cascade);

        }
    }
}
