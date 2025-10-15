namespace MenzaMate.Data.Generic
{
    public interface IRepository<TEntity> : IReadOnlyRepository<TEntity>
        where TEntity : class
    {
        void Add(TEntity entity);

        void Update(TEntity entity);

        void Delete(TEntity entity);

        Task SaveAsync();

    }
}
