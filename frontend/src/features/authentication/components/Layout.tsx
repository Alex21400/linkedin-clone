import React, { ReactNode } from 'react'
import { Link } from 'react-router-dom'

type LayoutProps = {
    onSignUpPage?: boolean;
    children: ReactNode
}

const Layout: React.FC<LayoutProps> = ({ onSignUpPage, children }) => {
  return (
    <div className={`grid grid-rows-[auto_1fr_auto] min-h-screen ${onSignUpPage ? 'bg-secondary' : ''}`}>
        <header className={`${onSignUpPage ? 'max-w-[74rem]' : ''} w-full my-0 mx-auto p-8`}>
          <Link to={"/"}>
            <img className={`${onSignUpPage ? 'w-32' : 'w-28 ml-8'}`} src="/logo.svg" alt="logo" />
          </Link>
        </header>
        <main className='w-full my-auto mx-auto'>
          {children}
        </main>
        <footer className='bg-white text-sm'>
          <ul className='max-w-[74rem] w-100 my-0 mx-auto p-8 flex items-center flex-wrap gap-4 text-darkGray text-xs'>
            <li className='flex items-center gap-2'>
              <img className='w-16' src="/logo-dark.svg" alt="logo-dark" />
              <span className='font-semibold'>2024</span>
            </li>
            <li>
              <Link to={""}>Accessiblity</Link>
            </li>
            <li>
              <Link to={""}>User Agreement</Link>
            </li>
            <li>
              <Link to={""}>Privacy Policy</Link>
            </li>
            <li>
              <Link to={""}>Cookie Policy</Link>
            </li>
            <li>
              <Link to={""}>Copywright Policy</Link>
            </li>
            <li>
              <Link to={""}>Brand Policy</Link>
            </li>
            <li>
              <Link to={""}>Guest Controls</Link>
            </li>
            <li>
              <Link to={""}>Community Guidelines</Link>
            </li>
            <li>
              <Link to={""}>Language</Link>
            </li>
          </ul>
        </footer>
    </div>
  )
}

export default Layout