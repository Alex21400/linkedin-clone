const Loader: React.FC = () => {
  return (
    <div className='flex flex-col items-center h-screen w-full'>
        <img 
            src='/logo.svg' 
            alt='Loading...'
            className='h-auto w-40 mt-[12rem]'
        />
        <div className="w-40 h-[3px] bg-[rgb(231,231,231)] rounded-full mt-4 relative overflow-hidden 
          before:content-[''] before:w-24 before:h-[3px] before:bg-primary before:absolute before:left-32 before:animate-slide">
        </div>
    </div>
  )
}

export default Loader