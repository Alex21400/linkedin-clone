import { ReactNode } from 'react'

type SeparatorProps = {
    children: ReactNode
}

const Separator: React.FC<SeparatorProps> = ({ children }) => {
  return (
    <div className="grid grid-cols-[1fr_auto_1fr] items-center gap-4 mt-4 before:content-[''] before:block before:my-4 before:h-px before:bg-[rgba(0,0,0,0.15)]
      after:content-[''] after:block after:my-4 after:h-px after:bg-[rgba(0,0,0,0.15)]">
        {children}
    </div>
  )
}

export default Separator