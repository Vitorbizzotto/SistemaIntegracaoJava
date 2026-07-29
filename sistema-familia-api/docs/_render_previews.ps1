$ErrorActionPreference = 'Stop'

$src = Join-Path $PSScriptRoot "Descomplicando a Arquitetura - Sistema Familia API.pptx"
$outDir = Join-Path $PSScriptRoot "previews"
if (!(Test-Path $outDir)) { New-Item -ItemType Directory -Path $outDir | Out-Null }

$ppt = New-Object -ComObject PowerPoint.Application
try {
    # MsoTriState: -1 = True, 0 = False
    # Open(FileName, ReadOnly, Untitled, WithWindow)
    $pres = $ppt.Presentations.Open($src, -1, 0, -1)
    try {
        # exporta cada slide como PNG (1600 x 900)
        for ($i = 1; $i -le $pres.Slides.Count; $i++) {
            $slide = $pres.Slides.Item($i)
            $out = Join-Path $outDir ("slide-{0:D2}.png" -f $i)
            $slide.Export($out, "PNG", 1600, 900)
        }
        Write-Host "Exportados $($pres.Slides.Count) slides em $outDir"
    } finally {
        $pres.Close()
    }
} finally {
    $ppt.Quit()
    [System.Runtime.Interopservices.Marshal]::ReleaseComObject($ppt) | Out-Null
}
