using MenzaMate.Data.Entities;
using MenzaMate.Data.Static;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

namespace MenzaMateBackend.Data.Entities
{
    public class Menu
    {
        public int MenuId { get; set; }
        public int? HistoryId { get; set; }
        public DateTime Date { get; set; }
        public string Title { get; set; }
        public string Description { get; set; }
        public ICollection<Rating> Ratings { get; set; } = new List<Rating>();
        public ICollection<FavoriteMenu> FavoriteMenus { get; set; } = new List<FavoriteMenu>();
        public MenuHistory MenuHistory { get; set; }
    }
    public class MenuConfigurationBuilder : IEntityTypeConfiguration<Menu>
    {
        public void Configure(EntityTypeBuilder<Menu> builder)
        {
            builder.HasKey(x => x.MenuId);

            builder
                .Property(x => x.Date)
                .IsRequired();


            builder
                .Property(x => x.Title)
                .IsRequired()
                .HasMaxLength(DataValidationConstants.LowMaxLength);

            builder
           .Property(x => x.Description)
           .HasMaxLength(DataValidationConstants.LowMaxLength);

            builder
          .Property(x => x.HistoryId)
          .IsRequired(false);
        }
    }
}


