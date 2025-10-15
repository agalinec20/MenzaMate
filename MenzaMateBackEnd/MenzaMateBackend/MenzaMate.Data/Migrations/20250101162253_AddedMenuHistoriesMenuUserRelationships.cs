using System;
using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

#pragma warning disable CA1814 // Prefer jagged arrays over multidimensional

namespace MenzaMate.Data.Migrations
{
    /// <inheritdoc />
    public partial class AddedMenuHistoriesMenuUserRelationships : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Menus_MenuHistories_HistoryId",
                table: "Menus");

            migrationBuilder.DropIndex(
                name: "IX_Menus_HistoryId",
                table: "Menus");

            migrationBuilder.DeleteData(
                table: "MenuHistories",
                keyColumn: "Id",
                keyValue: 1);

            migrationBuilder.DeleteData(
                table: "MenuHistories",
                keyColumn: "Id",
                keyValue: 2);

            migrationBuilder.RenameColumn(
                name: "Viewed",
                table: "MenuHistories",
                newName: "Added");

            migrationBuilder.AddColumn<int>(
                name: "MenuId",
                table: "MenuHistories",
                type: "int",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "UserId",
                table: "MenuHistories",
                type: "int",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.CreateIndex(
                name: "IX_MenuHistories_MenuId",
                table: "MenuHistories",
                column: "MenuId");

            migrationBuilder.CreateIndex(
                name: "IX_MenuHistories_UserId",
                table: "MenuHistories",
                column: "UserId");

            migrationBuilder.AddForeignKey(
                name: "FK_MenuHistories_Menus_MenuId",
                table: "MenuHistories",
                column: "MenuId",
                principalTable: "Menus",
                principalColumn: "MenuId",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_MenuHistories_Users_UserId",
                table: "MenuHistories",
                column: "UserId",
                principalTable: "Users",
                principalColumn: "UserId",
                onDelete: ReferentialAction.Cascade);
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_MenuHistories_Menus_MenuId",
                table: "MenuHistories");

            migrationBuilder.DropForeignKey(
                name: "FK_MenuHistories_Users_UserId",
                table: "MenuHistories");

            migrationBuilder.DropIndex(
                name: "IX_MenuHistories_MenuId",
                table: "MenuHistories");

            migrationBuilder.DropIndex(
                name: "IX_MenuHistories_UserId",
                table: "MenuHistories");

            migrationBuilder.DropColumn(
                name: "MenuId",
                table: "MenuHistories");

            migrationBuilder.DropColumn(
                name: "UserId",
                table: "MenuHistories");

            migrationBuilder.RenameColumn(
                name: "Added",
                table: "MenuHistories",
                newName: "Viewed");

            migrationBuilder.InsertData(
                table: "MenuHistories",
                columns: new[] { "Id", "Viewed" },
                values: new object[,]
                {
                    { 1, new DateTime(2024, 1, 15, 12, 0, 0, 0, DateTimeKind.Unspecified) },
                    { 2, new DateTime(2024, 2, 15, 12, 0, 0, 0, DateTimeKind.Unspecified) }
                });

            migrationBuilder.CreateIndex(
                name: "IX_Menus_HistoryId",
                table: "Menus",
                column: "HistoryId");

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
