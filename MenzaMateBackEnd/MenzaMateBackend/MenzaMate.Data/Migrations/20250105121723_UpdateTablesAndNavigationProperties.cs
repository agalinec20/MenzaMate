using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

#pragma warning disable CA1814 // Prefer jagged arrays over multidimensional

namespace MenzaMate.Data.Migrations
{
    /// <inheritdoc />
    public partial class UpdateTablesAndNavigationProperties : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {

            migrationBuilder.RenameColumn(
                name: "Id",
                table: "MenuHistories",
                newName: "MenuHistoryId");

            migrationBuilder.RenameColumn(
                name: "Id",
                table: "FavoriteMenus",
                newName: "FavoriteMenuId");

            migrationBuilder.RenameColumn(
                name: "Id",
                table: "Answers",
                newName: "AnswerId");


        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {

            migrationBuilder.RenameColumn(
                name: "MenuHistoryId",
                table: "MenuHistories",
                newName: "Id");

            migrationBuilder.RenameColumn(
                name: "FavoriteMenuId",
                table: "FavoriteMenus",
                newName: "Id");

            migrationBuilder.RenameColumn(
                name: "AnswerId",
                table: "Answers",
                newName: "Id");

        }
    }
}
