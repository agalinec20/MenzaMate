using MenzaMateBackend.Data.Entities;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

namespace MenzaMate.Data.Entities
{
    public class MenuHistory
    {
        public int MenuHistoryId { get; set; }
        public int UserId { get; set; }
        public int MenuId { get; set; }
        public DateTime Added { get; set; }
        public  User User { get; set; }
        public  Menu Menu { get; set; }
        public ICollection<Menu> Menus { get; set; } = new List<Menu>();
    }
    public class MenuHistoryConfigurationBuilder : IEntityTypeConfiguration<MenuHistory>
    {
        public void Configure(EntityTypeBuilder<MenuHistory> builder)
        {
            builder.HasKey(x => x.MenuHistoryId);
            builder.ToTable("MenuHistories");

        }
    }
}
