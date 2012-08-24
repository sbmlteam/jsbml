%% ============================================================================
%% Filename:     JSBMLdoc.cls
%% Description:  LaTeX style file For JSBML documents
%% Author(s):    Michael Hucka <mhucka@caltech.edu>,
%%               Andreas Draeger <andreas.draeger@uni-tuebingen.de>
%% Organization: California Institute of Technology & University of Tuebingen
%% Date created: 2012-04-06
%%
%% This is based in part on the LaTeX class "sbmlpkgspec.cls", created by
%% Michael Hucka in early 2012.
%%
%% ============================================================================
%% This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
%% for the latest version of JSBML and more information about SBML.
%%
%% Copyright (C) 2009-2012 jointly by the following organizations:
%% 1. The University of Tuebingen, Germany
%% 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
%% 3. The California Institute of Technology, Pasadena, CA, USA
%%
%% This library is free software; you can redistribute it and/or modify it
%% under the terms of the GNU Lesser General Public License as published by
%% the Free Software Foundation. A copy of the license agreement is provided
%% in the file named "LICENSE.txt" included with this software distribution
%% and also available online as <http://sbml.org/Software/JSBML/License>.
%% ============================================================================

%% It would be so nice to be able to put the CVS/RCS 'Date' tag in the
%% \filedate below.  Unfortunately, LaTeX will barf on the resulting string.

\def\filedate{2012/04/06}

\NeedsTeXFormat{LaTeX2e}

\ProvidesClass{JSBMLdoc}[\filedate\space JSBML documentation style]

%% ----------------------------------------------------------------------------
%% Package references and options.
%% ----------------------------------------------------------------------------

%% Keep in mind that hyperref needs to be almost the last package loaded.

\newif\if@draftdoc
\DeclareOption{draftdoc}{\global\let\if@draftdoc\iftrue}
\DeclareOption{finaldoc}{\global\let\if@draftdoc\iffalse}

\newif\if@notoc
\DeclareOption{toc}{\global\let\if@notoc\iffalse}
\DeclareOption{notoc}{\global\let\if@notoc\iftrue}

\newif\if@twocolumntoc
\DeclareOption{twocolumntoc}{\global\let\if@twocolumntoc\iftrue}
\DeclareOption{singlecolumntoc}{\global\let\if@twocolumntoc\iffalse}

\DeclareOption*{\PassOptionsToClass{\CurrentOption}{report}}

\ExecuteOptions{toc,twoside}
\ExecuteOptions{singlecolumntoc}
\ProcessOptions
\LoadClass{report}

%% ----------------------------------------------------------------------------
%% Dependencies on other packages.
%% ----------------------------------------------------------------------------

\RequirePackage{lastpage}
\RequirePackage[english]{babel}
\RequirePackage{ifpdf}
\RequirePackage{booktabs}
\RequirePackage{multicol}
\RequirePackage{array}
\RequirePackage{xspace}
\RequirePackage{scalefnt}
\RequirePackage{pifont}
\RequirePackage[pagewise,mathlines,edtable,right]{lineno}
\RequirePackage{calc}
\RequirePackage{fancyhdr}
\RequirePackage{fancybox}
\RequirePackage{amsmath}
\RequirePackage{amssymb}
\RequirePackage{amsfonts}
\RequirePackage{wasysym}
\RequirePackage{enumitem}
\RequirePackage{accsupp}
\RequirePackage{sidecap}
\RequirePackage{rotating}
\RequirePackage{hyphenat}
\RequirePackage[fit,breakall]{truncate}
\RequirePackage{wrapfig}
\RequirePackage{framed}
\RequirePackage[section]{placeins}
\RequirePackage[square,numbers,sort]{natbib}
\RequirePackage{tocloft}

% We use the "optional" package to indicate whether to generate a grayscale
% vs a color version of the document without having to alter the .tex file
% itself.  Unfortunately, the "optional" package doesn't offer an "or" type
% switch or a way to set a default, so we have to go through some
% contortions here.  Also, it needs *something* in the optional argument
% to RequirePackage or UsePackage, hence the "dummyoption" thing below.
%
% To produce grayscale output without modifying the source file, invoke
% latex with a command of the following form:
%
%  pdflatex "\newcommand\UseOption{grayscale}\input{sbml-level-2-version-2}"

\newif\ifgrayscalespec
\global\let\ifgrayscalespec\iffalse
\RequirePackage[dummyoption]{optional}
\opt{grayscale}{\global\let\ifgrayscalespec\iftrue}

% Load varioref only if not generating HTML, because on HTML
% pages it doesn't make sense to use varioref.

% \RequirePackage{html}

% \latexhtml{
%begin{latexonly}
  \RequirePackage[american]{varioref}
%end{latexonly}
% }{
%  \newcommand{\vref}[1]{\ref{#1}}
% }

% Hyperref, xcolor, graphicx and possibly others have a flag "pdftex"
% that needs to be used if pdflatex is being used.  The following puts
% these inside a conditional for that situation.

\ifpdf
  % Case: using pdflatex

  \RequirePackage{graphicx}

  % Options get even more complicated.  If we're producing grayscale output,
  % we don't want to bother with coloring links, but we still want to load
  % hyperref so that its macros are defined (and we don't have to redefine
  % everything that uses hyperref).  So:

  \ifgrayscalespec
    \RequirePackage[pdftex,breaklinks=true,colorlinks=false,
    bookmarksnumbered=true]{hyperref}
  \else
    \RequirePackage[pdftex,breaklinks=true,colorlinks=true,
    bookmarks=true,bookmarksopen=true,bookmarksopenlevel=2,
    pdfhighlight=/O,linkcolor={sbmlblue},citecolor={sbmlblue},
    urlcolor={sbmlblue},anchorcolor={sbmlblue},bookmarksnumbered]{hyperref}
  \fi

  % Although it may seem like we wouldn't need to load xcolor if
  % the grayscale option is given, in fact it doesn't affect anything
  % to load this and it avoids having to conditionalize other things.
  % (The colors aren't actually invoked if grayscale is being used.)

  \RequirePackage[pdftex,rgb,dvipsnames,svgnames,hyperref,table]{xcolor}
\else
  % Case: not using pdflatex

  \RequirePackage{graphicx}
  % Test whether we're being run from latex or latex2html
  % \latexhtml{
    % For latex.
    \RequirePackage[breaklinks]{hyperref}
  % }{
    % For latex2html.
    % \RequirePackage[latex2html,breaklinks]{hyperref}
  % }
  \RequirePackage[rgb,dvipsnames,svgnames,hyperref,table]{xcolor}
\fi

% The remaining packages are best loaded after hyperref.

\RequirePackage{nameref}

% Load listings package & set some values.

\RequirePackage{listings}
\lstloadlanguages{Java,[Visual]C++,bash,csh,Matlab,make,XML,HTML}

% Load draftwatermark if this document is a draft version.

\if@draftdoc
  \RequirePackage[firstpage]{draftwatermark}
  \SetWatermarkLightness{0.92}
\fi

\RequirePackage{overpic}

%% ----------------------------------------------------------------------------
%% Font selection.
%% ----------------------------------------------------------------------------

% This sets up Helvetica for headings and TX Typewriter for the tt font.
% The font scaling is because the default Helvetica size is too big.

\RequirePackage[T1]{fontenc}
\RequirePackage{fourier}
\RequirePackage{helvet}
\ifgrayscalespec
  % In the grayscale version of the spec, Helvetica bold is used for class
  % names in the text, and the width of the words then is too big unless
  % we scale down the font even more compared to the regular case below.
  \def\Hv@scale{0.814}
\else
  \def\Hv@scale{0.87}
\fi

% The following sets up txtt for the typewriter font.

\renewcommand{\ttdefault}{txtt}
\DeclareMathAlphabet{\mathtt}{T1}{txtt}{m}{n}
\SetMathAlphabet{\mathtt}{bold}{T1}{txtt}{b}{n}

% The next bit is an adaption of code from ot1phv.fd and adapted to the txtt
% fonts.  The txtt fonts are just a tad too big, so this tries to rescale
% them down a tiny bit.  This isn't completely right because I couldn't
% figure out the right syntax when the DeclareFontShape uses ssub below.
% (Notice how the ones with ssub don't have the \Txtt@@scale factor.)

\def\Txtt@scale{0.97}
\edef\Txtt@@scale{s*[\csname Txtt@scale\endcsname]}%

\DeclareFontFamily{T1}{txtt}{\hyphenchar \font\m@ne}
\DeclareFontShape{T1}{txtt}{m}{n}{	%rebular
     <-> \Txtt@@scale txtt%
}{}
\DeclareFontShape{T1}{txtt}{m}{sc}{	%cap & small cap
     <-> \Txtt@@scale txttsc%
}{}
\DeclareFontShape{T1}{txtt}{m}{sl}{	%slanted
     <-> \Txtt@@scale txttsl%
}{}
\DeclareFontShape{T1}{txtt}{m}{it}{	%italic
     <-> ssub * txtt/m/sl%
}{}
\DeclareFontShape{T1}{txtt}{m}{ui}{	%unslanted italic
     <-> ssub * txtt/m/sl%
}{}
\DeclareFontShape{T1}{txtt}{b}{n}{	%bold
     <-> \Txtt@@scale txbtt%
}{}
\DeclareFontShape{T1}{txtt}{b}{sc}{	%bold cap & small cap
     <-> \Txtt@@scale txbttsc%
}{}
\DeclareFontShape{T1}{txtt}{b}{sl}{	%bold slanted
     <-> \Txtt@@scale txbttsl%
}{}
\DeclareFontShape{T1}{txtt}{b}{it}{	%bold italic
     <-> ssub * txtt/b/sl%
}{}
\DeclareFontShape{T1}{txtt}{b}{ui}{	%bold unslanted italic
     <-> ssub * txtt/b/sl%
}{}
\DeclareFontShape{T1}{txtt}{bx}{n}{	%bold extended
     <-> ssub * txtt/b/n%
}{}
\DeclareFontShape{T1}{txtt}{bx}{sc}{	%bold extended cap & small cap
     <-> ssub * txtt/b/sc%
}{}
\DeclareFontShape{T1}{txtt}{bx}{sl}{	%bold extended slanted
     <-> ssub * txtt/b/sl%
}{}
\DeclareFontShape{T1}{txtt}{bx}{it}{	%bold extended italic
     <-> ssub * txtt/b/sl%
}{}
\DeclareFontShape{T1}{txtt}{bx}{ui}{	%bold extended unslanted italic
     <-> ssub * txtt/b/sl%
}{}

% Adjustments due to quirks of the combination of fourier and amsmath:

% Make the \big( braces regular height (default is too short):
\delimiterfactor=1000
\delimitershortfall=1pt

%% ----------------------------------------------------------------------------
%% Main code.
%% ----------------------------------------------------------------------------

\raggedbottom

% Local color definitions.

\definecolor{sbmlblue}{rgb}{0.08,0.51,0.77}
\definecolor{sbmlgray}{gray}{0.7}
\definecolor{sbmlrowgray}{gray}{0.97}
\definecolor{specialblue}{rgb}{0.015,0.38,0.6}
\definecolor{extremelylightgray}{gray}{0.97}
\definecolor{verylightgray}{gray}{0.9}
\definecolor{lightgray}{gray}{0.8}
\definecolor{mediumgray}{gray}{0.5}
\definecolor{darkgray}{gray}{0.3}
\definecolor{almostblack}{gray}{0.22}
\definecolor{lightyellow}{rgb}{0.98,0.94,0.7}
\definecolor{verylightyellow}{rgb}{0.97,0.95,0.85}
\definecolor{darkblue}{rgb}{0.1,0.4,0.55}
\definecolor{mediumgreen}{rgb}{0.1,0.6,0.3}
\definecolor{royalblue}{cmyk}{.93, .79, 0, 0}
\definecolor{lightblue}{cmyk}{.10, .017, 0, 0}
\definecolor{forrestgreen}{cmyk}{.76, 0, .76, .45}
\definecolor{darkred}{rgb}{.7,0,0}
\definecolor{winered}{cmyk}{0,1,0.331,0.502}

% Color for marking changed text, conditional on whether gray scale mode is on.

\ifgrayscalespec
  \newcommand{\sbmlchangedcolor}{\normaltextcolor}
\else
  \definecolor{sbmlchangedcolor}{rgb}{0.69,0.19,0.376}
  \newcommand{\sbmlchangedcolor}{Maroon}
\fi

% Macros for making consistent font, color, and other selections:

\newcommand{\normaltextcolor}{almostblack}

\newcommand{\changed}[1]{\textcolor{\sbmlchangedcolor}{#1}}
\newenvironment{blockChanged}{\color{\sbmlchangedcolor}}{\color{\normaltextcolor}}

\newcommand{\figureFont}[1]{\textsf{\textbf{#1}}}

\newcommand{\literalFont}[1]{\textcolor{black}{\textup{\texttt{#1}}}}
\newcommand{\literalFontNC}[1]{\textup{\texttt{#1}}}

\newcommand{\tightspacing}{\renewcommand{\baselinestretch}{0.85}}
\newcommand{\regularspacing}{\renewcommand{\baselinestretch}{1.0}}

\ifgrayscalespec
  \newcommand{\defRef}[2]{\textbf{\class{#1}}\xspace}
  \newcommand{\absDefRef}[2]{\textbf{\abstractclass{#1}}\xspace}
\else
  \newcommand{\defRef}[2]{\class{\hyperref[#2]{#1}}\xspace}
  \newcommand{\absDefRef}[2]{\abstractclass{\hyperref[#2]{#1}}\xspace}
\fi

% Hyperref extras:

\newcommand{\link}[2]{\literalFont{\href{#1}{#2}}}
\newcommand{\mailto}[1]{\link{mailto:#1}{#1}}

% 'lineno' package adjustments.
% The BeginAccSupp business causes the line numbers to be ignored for
% copy-paste operations in the PDF output.

\setlength{\linenumbersep}{2.2em}
\renewcommand{\thelinenumber}{%
  \BeginAccSupp{ActualText={}}%
  \textcolor{mediumgray}{\parbox[b][\height+1.5pt][c]{10pt}{\arabic{linenumber}}}%
  \EndAccSupp{}%
}

% 'booktabs' package adjustments:

\setlength{\cmidrulewidth}{0.3 pt}
\setlength{\lightrulewidth}{0.3 pt}
\setlength{\heavyrulewidth}{0.9 pt}

% Fix placement of figures & tables.  This keeps latex from shoving big
% floats to the end of a document when they are somewhat big, which it will
% do even if you put [htb] as the argument.

\setcounter{topnumber}{2}            % max num of floats at top of page
\setcounter{bottomnumber}{2}         % max num of floats at bottom of page
\renewcommand{\topfraction}{.95}        % fraction that a top float can cover
\renewcommand{\bottomfraction}{.95}     % fraction a bottom float can cover
\renewcommand{\textfraction}{0.05}      % >5% of a non-float page must be text
\renewcommand{\floatpagefraction}{0.95} % float page must be 95% full

% Spacing of floats.

\setlength{\intextsep}{17pt plus 6pt minus 4pt}

% Make floats that appear alone on a page appear at the top, rather than
% (as is the LaTeX default) appearing centered vertically on the page.

\setlength{\@fptop}{5pt}

% Margin adjustments. I've tried using vmargin.sty, but it interacts badly
% with page numbers at the bottom of the page, so I've resorted to hardcoding
% the dimensions like this.

\setlength{\marginparwidth}{0.77in}
\setlength{\marginparsep}{3pt}

\setlength{\oddsidemargin}{0 pt}
\setlength{\evensidemargin}{0 pt}
\setlength{\topmargin}{1.5 ex}
\setlength{\headsep}{2em}
\setlength{\footskip}{3em}
\setlength{\voffset}{-0.75 in}
\setlength{\hoffset}{0 in}
\setlength{\headheight}{18pt}
\setlength{\textwidth}{6.5 in}
\setlength{\textheight}{9.25 in}

% Change the style of the table of contents.
% Much of this is from the 'tocloft' package.

\renewcommand{\cfttoctitlefont}{\LARGE\bfseries\sffamily}
\renewcommand{\cftchapfont}{\bfseries\sffamily\normalsize}
\renewcommand{\cftsecfont}{\sffamily\small}
\renewcommand{\cftsubsecfont}{\sffamily\small}
\renewcommand{\cftsubsubsecfont}{\sffamily\small}
\renewcommand{\cftchappagefont}{\bfseries\sffamily}
\renewcommand{\cftsecpagefont}{\sffamily}
\renewcommand{\cftsubsecpagefont}{\sffamily}
\renewcommand{\cftsubsubsecpagefont}{\sffamily}
\setlength{\cftaftertoctitleskip}{0.5em}
\setlength{\cftbeforechapskip}{0.5em}
\setcounter{tocdepth}{2}

% Title page macros.  Severly hacked from originals in LaTeX's article.cls.

\def\frontNotice#1{\gdef\@frontNotice{#1}}

\def\version#1{\gdef\@version{#1}}

\def\@puttitle{%
  \scalebox{1}[0.9]{%
    \fcolorbox{black}{specialblue}{%
      \begin{minipage}{\textwidth - 7pt}%
        \vspace*{6pt}%
        \centering%
        \textcolor{white}{\sffamily\bfseries\huge \@title}
      \vspace*{6pt}%
    \end{minipage}}}}

\def\@putauthorinfo{%
  \large
  {\@author}}

\def\maketableofcontents{%
  \if@notoc
  \else
    \begingroup
      % Tighten spacing between lines within an entry.
      % This assumes 10 pt font!
      \setlength{\baselineskip}{10.5pt}%
      \if@twocolumntoc
        \setlength{\columnsep}{16pt}%
        \begin{multicols}{2}
      \fi
      \tableofcontents%
      \if@twocolumntoc
        \end{multicols}
      \fi
      \addtolength{\parskip}{1.45 ex}%
    \endgroup
    \clearpage
  \fi}

% Need redefine \tableofcontents to counter effects of hacking \section below.

\renewcommand\tableofcontents{%
  \@startsection{section}{0}{0pt}{-1.8ex \@plus -1ex \@minus -.2ex}%
  {0.8ex}{\normalfont\Large\bfseries\sffamily}*% The star fakes a \section*
  \contentsname%
  \@mkboth{\MakeUppercase\contentsname}{\MakeUppercase\contentsname}%
  \@starttoc{toc}%
  }

\renewcommand\maketitle{\par
  \begingroup
  \renewcommand\thefootnote{\@fnsymbol\c@footnote}%
  \def\@makefnmark{\rlap{\@textsuperscript{\normalfont\@thefnmark}}}%
  \long\def\@makefntext##1{\parindent 1em\noindent
    \hb@xt@1.8em{%
      \hss\@textsuperscript{\normalfont\@thefnmark}}##1}%
  \if@twocolumn
    \ifnum \col@number=\@ne
      \@maketitle
    \else
      \twocolumn[\@maketitle]%
    \fi
  \else
    \global\@topnum\z@   % Prevents figures from going at top of page.
    \@maketitle
  \fi
  \thispagestyle{empty}\@thanks
  \endgroup
  \setcounter{footnote}{0}%
  \global\let\thanks\relax
  \global\let\maketitle\relax
  \global\let\@maketitle\relax
  \global\let\@thanks\@empty
  \global\let\@author\@empty
  \global\let\@date\@empty
  \global\let\@authoremail\@empty
  \global\let\title\relax
  \global\let\author\relax
  \global\let\date\relax
  \global\let\and\relax
  }

\newcommand\maketitlepage{\par
  \begingroup
  \renewcommand\thefootnote{\@fnsymbol\c@footnote}%
  \def\@makefnmark{\rlap{\@textsuperscript{\normalfont\@thefnmark}}}%
  \long\def\@makefntext##1{\parindent 1em\noindent
    \hb@xt@1.8em{%
      \hss\@textsuperscript{\normalfont\@thefnmark}}##1}%
  \vskip 2em%
  \if@twocolumn
    \ifnum \col@number=\@ne
      \@maketitle
    \else
      \twocolumn[\@maketitle]%
    \fi
  \else
    \newpage
    \global\@topnum\z@   % Prevents figures from going at top of page.
    \@maketitle
  \fi
  \thispagestyle{empty}\@thanks
  \vfill
  \centering%
  \ifx\@frontNotice\undefined
  \relax
  \else
    {\begin{minipage}{5.75in}\@frontNotice\end{minipage}}%
  \fi
  \vskip 2em
  \vfill
  \centerline{\includegraphics[width=1.25in]{\JSBMLlogo}}
  \clearpage
  \endgroup
  \setcounter{footnote}{0}%
  \global\let\thanks\relax
  \global\let\maketitle\relax
  \global\let\@maketitle\relax
  \global\let\@thanks\@empty
  \global\let\@author\@empty
  \global\let\@date\@empty
  \global\let\@authoremail\@empty
  \global\let\title\relax
  \global\let\author\relax
  \global\let\date\relax
  \global\let\and\relax
  }

\def\@maketitle{%
  \newpage
  \null
  \begin{center}%
    \let \footnote \thanks
    \@puttitle
    \vskip 0.5em%
    \ifx\@version\undefined
    \relax
    \else
    {\large Version: \@version}%
    \fi
    \vskip 3em%
    {\lineskip .5em%
      \@putauthorinfo
      }%
    \vfill%
  \end{center}%
  \par%
  \vskip 1.5em%
  \ifpdf
    \hypersetup{pdftitle={\@title}}
  \fi%
  }


% Including the logo on the front page.  We switch which copy of the logo
% file we use, depending on various factors, and we switch which file format
% is used depending on the output we're producing. 

\ifgrayscalespec
  \newcommand{\logoBaseFile}{JSBML_shaddow_gray}
  \newcommand{\logoBaseFileSmall}{JSBML_shaddow_gray_small}
\else
  \newcommand{\logoBaseFile}{JSBML_shaddow}
  \newcommand{\logoBaseFileSmall}{JSBML_shaddow_small}
\fi

\ifpdf
  \newcommand{\JSBMLlogo}{\logoBaseFile.pdf}%
  \newcommand{\JSBMLlogoSmall}{JSBML_shaddow.pdf}%{\logoBaseFileSmall.png}%
\else%
  \newcommand{\JSBMLlogo}{\logoBaseFile.eps}%
  \newcommand{\JSBMLlogoSmall}{\logoBaseFileSmall.eps}%
\fi


% Graphics adjustments.  The path setup is so that the \includegraphics
% in the @puttile definition can find the logo file no matter where the
% document is located (but obviously, it only works for certain path
% combinations -- it's a total hack).

\graphicspath{{./logo/}{../logo/}{../../logo/}{../common/logo/}{../../common/logo/}{../tex/logo/}{../project/tex/logos/}{../../project/tex/logos/}{../../../project/tex/logos/}{../../../../project/tex/logos/}}


% Definition of page styles with headers and footers.

\fancyhf{}

\newcommand{\footchpsection}{\@title ~~$\blacksquare$~ \nouppercase{\leftmark}}
\newcommand{\footpage}{Page \thepage\ of \pageref*{LastPage}}

\renewcommand{\footrule}{\color{sbmlblue}\footnotesize{\hbox to\headwidth{\dotfill}}\vskip-2ex}

\fancypagestyle{headerandfooter}{%
  \renewcommand{\headrulewidth}{0.25pt}
  \renewcommand{\headrule}{\vskip-1ex\color{sbmlblue}\footnotesize{\hbox to\headwidth{\dotfill}}}
  \lhead{}%
  \rhead{\color{darkgray}\small\textsf{Section \nouppercase{\rightmark}}}%
  \cfoot{}%
  \lfoot{\color{darkgray}\small\textsf{\footchpsection}}%
  \rfoot{\color{darkgray}\small\textsf{\footpage}}%
}

\fancypagestyle{footeronly}{%
  \renewcommand{\headrule}{}%
  \lhead{}%
  \rhead{}%
  \lfoot{\color{darkgray}\small\textsf{\footchpsection}}%
  \rfoot{\color{darkgray}\small\textsf{\footpage}}%
}

\fancypagestyle{plain}{%
  \renewcommand{\headrule}{}%
  \lhead{}%
  \rhead{}%
  \lfoot{}%
  \rfoot{\color{darkgray}\small\textsf{\footpage}}%
}

\pagestyle{headerandfooter}


% Definition of paragraph style.

\setlength{\parindent}{0 pt}            % Unindented paragraphs, separated ...
\setlength{\parskip}{1.3 ex}            % ... by roughly one blank line.
\setlength{\partopsep}{-1ex plus 0.1ex minus -0.2ex}
\setlength{\itemsep}{-0.25ex plus 0.15ex}


% \topsep is supposed to affect list environments like itemize,
% but does nothing there.  Instead, it affects environments like tabular.

\setlength{\topsep}{0.3ex plus 0.1ex minus -0.2ex}


% Definition of section heading style.

\@addtoreset{chapter}{part}

\renewcommand{\chapter}{\@startsection%
  {chapter}{0}{0pt \@plus 1ex}{-3ex \@plus -1ex \@minus -.2ex}%
  {1.2ex}{\clearpage\thispagestyle{footeronly}%
    \hspace*{-24.25pt}\color{specialblue}{\rule[1.8em]{6.84in}{0.6em}}\color{\normaltextcolor}%
    \hspace*{-6.84in}\color{specialblue}{\rule[-0.8em]{6.84in}{0.1em}}\color{\normaltextcolor}%
    \hspace*{-0.57in}\raisebox{-4pt}{\includegraphics[width=0.6in]{\JSBMLlogoSmall}}%
    \hspace*{-0.03in}\noindent\normalfont\LARGE\bfseries\sffamily\hspace*{-6.84in}}}

\renewcommand{\section}{\@startsection%
  {section}{1}{0pt}{-2ex \@plus 1ex \@minus -.2ex}%
  {0.1ex}{\Large\bfseries\sffamily}}

\renewcommand{\subsection}{\@startsection%
  {subsection}{2}{0pt}{-1.5ex \@plus 1ex \@minus -.2ex}%
  {0.4ex}{\large\bfseries\sffamily\slshape}}

\renewcommand{\subsubsection}{\@startsection%
  {subsubsection}{3}{0pt}{-1.25ex \@plus 1ex \@minus -.2ex}%
  {0.4ex}{\normalsize\bfseries\sffamily\slshape}}

% The References heading needs to be fixed up to account for shift
% we apply to section headings.  These defs are modified from article.cls.

\bibliographystyle{siam}

\renewenvironment{thebibliography}[1]{%
  \chapter*{References}%
  \markboth{References}{References}%
  \pagestyle{plain}%
  \addcontentsline{toc}{chapter}{\protect\numberline{\normalsize\refname}}%
  \list{\@biblabel{\@arabic\c@enumiv}}%
  {\settowidth\labelwidth{\@biblabel{#1}}%
    \leftmargin\labelwidth
    \advance\leftmargin\labelsep
    \@openbib@code
    \usecounter{enumiv}%
    \let\p@enumiv\@empty
    \renewcommand\theenumiv{\@arabic\c@enumiv}}%
  \sloppy
  \clubpenalty4000
  \@clubpenalty \clubpenalty
  \widowpenalty4000%
  \sfcode`\.\@m}
{\def\@noitemerr
  {\@latex@warning{Empty `thebibliography' environment}}%
  \endlist}

\newcommand{\sectionspecial}{\@startsection%
  {section}{1}{0pt}{-1.8ex \@plus -1ex \@minus -.2ex}%
  {0.8ex}{\hspace*{-41.1pt}\colorbox{sbmlgray}{\hspace*{6.99in}}\nopagebreak\\%
  \hspace*{-20pt}\normalfont\Large\bfseries\sffamily}}

% Redefine table & figure to use smaller font, sans serif,
% and to center contents by default:

\renewenvironment{table}[1][]  {\@float{table}[#1]\small\sffamily\centering}    {\end@float}
\renewenvironment{table*}[1][] {\@dblfloat{table}[#1]\small\sffamily\centering} {\end@dblfloat}
\renewenvironment{figure}[1][] {\@float{figure}[#1]\small\sffamily\centering}   {\end@float}
\renewenvironment{figure*}[1][]{\@dblfloat{figure}[#1]\small\sffamily\centering}{\end@dblfloat}


% The following was ripped out of caption.sty, version 1.4b.
% Copyright (C) 1994-95 Harald Axel Sommerfeldt
% The first few lines set up the parameters for the layout created
% by this style file.

\newcommand{\captionsize}{\small}
\newcommand{\captionfont}{\captionsize\sffamily\slshape}
\newcommand{\captionlabelfont}{\captionsize\bfseries\slshape}
\newlength{\captionmargin}
\setlength{\captionmargin}{6ex}

\setlength{\belowcaptionskip}{1ex \@plus 0.5ex}
\setlength{\abovecaptionskip}{1.5ex \@plus 0.5ex}

\newsavebox{\as@captionbox}
\newlength{\as@captionwidth}
\newcommand{\as@normalcaption}[2]{%
  #1 #2\par}
\let\as@caption\as@normalcaption
\newcommand{\as@centercaption}[2]{%
  \parbox[t]{\as@captionwidth}{{\centering#1 #2\par}}}
\let\as@shortcaption\as@centercaption
\newcommand{\as@makecaption}[2]{%
  \setlength{\leftskip}{\captionmargin}%
  \setlength{\rightskip}{\captionmargin}%
  \addtolength{\as@captionwidth}{-2\captionmargin}%
  \renewcommand{\baselinestretch}{0.9}
  \captionfont%
  \sbox{\as@captionbox}{{\captionlabelfont #1:} #2}%
  \ifdim \wd\as@captionbox >\as@captionwidth
    \as@caption{{\captionlabelfont #1:}}{#2}%
  \else%
    \as@shortcaption{{\captionlabelfont #1:}}{#2}%
  \fi}
\renewcommand{\@makecaption}[2]{%
  \vskip\abovecaptionskip%
  \setlength{\as@captionwidth}{\linewidth}%
  \as@makecaption{#1}{#2}%
  \vskip\belowcaptionskip}
\ifx\@makerotcaption\undefined
\else
  \typeout{\space\space\space\space\space\space\space\space\space
           `rotating' package detected}
  \renewcommand{\@makerotcaption}[2]{%
    \renewcommand{\baselinestretch}{0.9}
    \captionfont%
    \sbox{\as@captionbox}{{\captionlabelfont #1:} #2}%
    \ifdim \wd\as@captionbox > .8\vsize
      \rotatebox{90}{%
        \setlength{\as@captionwidth}{.8\textheight}%
        \begin{minipage}{\as@captionwidth}%
          \as@caption{{\captionlabelfont #1:}}{#2}%
        \end{minipage}}\par
    \else%
      \rotatebox{90}{\usebox{\as@captionbox}}%
    \fi
    \hspace{12pt}}
\fi
\ifx\floatc@plain\undefined
\else
  \typeout{\space\space\space\space\space\space\space\space\space
           `float' package detected}
  \renewcommand\floatc@plain[2]{%
    \setlength{\as@captionwidth}{\linewidth}%
    \as@makecaption{#1}{#2}}
  \ifx\as@ruled\undefined
  \else
    \renewcommand\floatc@ruled[2]{%
      \setlength{\as@captionwidth}{\linewidth}%
      \renewcommand{\baselinestretch}{0.9}
      \captionfont%
      \as@caption{{\captionlabelfont #1:}}{#2}}
  \fi
\fi

%
% Additional new commands.
%

% Code examples.
% First set the properties we use for all calls to the listing environment.

\newcommand{\code}[1]{\literalFont{#1}}     % Double, SId, SBOTerm, etc.
\newcommand{\codeNC}[1]{\literalFontNC{#1}} % Same, but without coloring.

\newboolean{includeCodeExample}
\setboolean{includeCodeExample}{false}

\lstset{escapechar=|,%
    language=Java,%
    columns=fullflexible,%
    keepspaces=true,%
    backgroundcolor=\color{extremelylightgray},%
    rulecolor=\color{sbmlgray},%
    frame=single,%
    numberstyle=\tiny\sffamily,%
    basicstyle=\small\ttfamily,%
    xleftmargin=3.5pt,%
    xrightmargin=3.5pt,%
    aboveskip=1\baselineskip,%
    belowskip=0.4\baselineskip,%
    commentstyle=\footnotesize\itshape\color{mediumgray},%
    tabsize=2,%
    captionpos=b}

\lstdefinestyle{java}{%
    language=Java,%
    keywordstyle=\ttfamily\bfseries\color{black}}

\lstdefinestyle{bash}{%
    language=bash,%
    keywordstyle=\mdseries}

\newcommand{\examplespacing}{\renewcommand{\baselinestretch}{0.88}}

\lstnewenvironment{example}[1][]
  {\lstset{#1}\examplespacing \csname lst@SetFirstLabel\endcsname}
  {\regularspacing \csname lst@SaveFirstLabel\endcsname}

\newcommand{\exampleFile}[2][numbers=left,style=java]{%
  \lstset{#1}\examplespacing\lstinputlisting{#2}\regularspacing}


% Margin note commands.
% The \reversemarginpar is to put notes in the left margin.

\reversemarginpar

\newcommand{\notice}{\marginpar{\hspace*{34pt}\raisebox{-0.5ex}{\Large\ding{43}}}}
\newcommand{\warning}{\marginpar{\hspace*{34pt}{\large\danger}}}

% Version using todonotes.
% Todonotes is nice and offers things like a list of notes, but it uses
% TikZ, and that's a heavy package to load every time.  I got annoyed by
% how much it slowed down my latex runs that I created a simple note
% alternative instead
% 
% \RequirePackage[textsize=scriptsize]{todonotes}
%
% \if@draftdoc
%   \newcommand{\sbmlshownotes}{}
% \else
%   \newcommand{\sbmlshownotes}{disable}
% \fi
%
% \newcommand{\draftnote}[1]{\todo[backgroundcolor=lightyellow,%
%  bordercolor=lightgray,linecolor=mediumgray,\sbmlshownotes]{\textit{#1}}}

\newcommand{\draftnoteForced}[1]{\mbox{}\marginpar{\hspace{0pt}\fcolorbox{Gold}{lightyellow}
    {\scriptsize\begin{minipage}[t]{0.65in}\raggedright\textit{#1}\end{minipage}}}}

\if@draftdoc
  \newcommand{\draftnote}[1]{\draftnoteForced{#1}}
\else
  \newcommand{\draftnote}[1]{}
\fi


% Cross-references.  This uses the nameref package.  Usage is as follows:
%
% * Use \vref{...} for everything and it will insert Figure~X, Table~Y,
%   Section~Z, etc., as appropriate for the reference.  You do not need to
%   add the object name; in other words, don't write "Section~\vref{foo}",
%   write just "vref{foo}".
%
% * Use \vref*{...} if the reference is in parentheses, like "(\vref*{...})".
%
% * At the beginning of a paragraph (and ONLY there), use \Vref*{...}.
%
% * For a range, use \vrefrange{startlabel}{endlabel}.
%
% To see actual examples of using this, grep for "vref" in *.tex in the
% JSBML User Guide directory.

\labelformat{chapter}{Chapter~#1}
\labelformat{section}{Section~#1}
\labelformat{subsection}{Section~#1}
\labelformat{subsubsection}{Section~#1}
\labelformat{table}{Table~#1}
\labelformat{figure}{Figure~#1}


% Misc.

\newcommand{\dblquote}{\verb|"|}   %"
\def\TReg{\textsuperscript{\textregistered}\,}
\def\TCop{\textsuperscript{\textcopyright}\,}
\def\TTra{\texttrademark\,}

% 
% Final settings before the body begins.
%

\color{\normaltextcolor}                % Default body font color.

%% -----------------------------------------------------------------------------
%% End of file `cekarticle.cls'.
%% -----------------------------------------------------------------------------
