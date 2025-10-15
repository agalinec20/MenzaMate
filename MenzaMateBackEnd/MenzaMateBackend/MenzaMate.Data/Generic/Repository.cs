using MenzaMate.Data.Stores;

namespace MenzaMate.Data.Generic
{
    public class Repository<TEntity> : ReadOnlyRepository<TEntity>, IRepository<TEntity>
        where TEntity : class
    {
        public Repository(MenzaMateContext context) : base(context)
        {
        }
        public void Add(TEntity entity)
        {
            _context.Add(entity);
        }

        public void Update(TEntity entity)
        {
            _context.Update(entity);
        }

        public void Delete(TEntity entity)
        {
            _context.Remove(entity);
        }

        public async Task SaveAsync()
        {
            await _context.SaveChangesAsync();
        }
    }
}
