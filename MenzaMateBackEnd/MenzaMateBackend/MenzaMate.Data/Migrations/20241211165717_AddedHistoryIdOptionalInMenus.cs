using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace MenzaMate.Data.Migrations
{
    /// <inheritdoc />
    public partial class AddedHistoryIdOptionalInMenus : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Menus_MenuHistories_HistoryId",
                table: "Menus");

            migrationBuilder.AlterColumn<int>(
                name: "HistoryId",
                table: "Menus",
                type: "int",
                nullable: true,
                oldClrType: typeof(int),
                oldType: "int");

            migrationBuilder.AddForeignKey(
                name: "FK_Menus_MenuHistories_HistoryId",
                table: "Menus",
                column: "HistoryId",
                principalTable: "MenuHistories",
                principalColumn: "Id");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Menus_MenuHistories_HistoryId",
                table: "Menus");

            migrationBuilder.AlterColumn<int>(
                name: "HistoryId",
                table: "Menus",
                type: "int",
                nullable: false,
                defaultValue: 0,
                oldClrType: typeof(int),
                oldType: "int",
                oldNullable: true);

            migrationBuilder.AddForeignKey(
                name: "FK_Menus_MenuHistories_HistoryId",
                table: "Menus",
                column: "HistoryId",
                principalTable: "MenuHistories",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
        }
    }
}
